package com.debuggor.mockinterview.interview.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.debuggor.mockinterview.common.bean.Message;
import com.debuggor.mockinterview.common.config.AlipayConfig;
import com.debuggor.mockinterview.common.enumerate.*;
import com.debuggor.mockinterview.common.service.MessageService;
import com.debuggor.mockinterview.common.util.ActivateCodeUtil;
import com.debuggor.mockinterview.common.util.OrdersNumberUtil;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Flow;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.bean.Order;
import com.debuggor.mockinterview.interview.service.FinderService;
import com.debuggor.mockinterview.interview.service.FlowService;
import com.debuggor.mockinterview.interview.service.InterviewerService;
import com.debuggor.mockinterview.interview.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝支付controller
 * 填写订单-付款-面试-评价  整个流程
 */
@Controller
@RequestMapping("/pay")
public class AlipayController {

    private Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private InterviewerService interviewerService;
    @Autowired
    private FinderService finderService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private MessageService messageService;

    /**
     * 用户提交订单页面
     *
     * @return
     */
    @RequestMapping("/order/{iid}")
    public String orderPage(@PathVariable("iid") Integer iid, HttpSession session, Model model) {
        Interviewer interviewer = interviewerService.getInterviewerById(iid);
        model.addAttribute("interviewer", interviewer);
        Finder finder = (Finder) session.getAttribute("finder");
        model.addAttribute("finder", finder);
        return "front/interview/order";
    }

    /**
     * 订单表中插入一条记录，跳转到用户确认订单支付的页面
     * 消息表中插入一条消息（用户待付款）
     *
     * @return
     */
    @RequestMapping("/toPay")
    public String toPay(Integer interviewerId, String introduction,
                        HttpSession session, Model model) {
        Finder finder = (Finder) session.getAttribute("finder");
        Interviewer interviewer = interviewerService.getInterviewerById(interviewerId);

        Order order = new Order();
        // 订单号 30位 单一无二
        String orderNum = OrdersNumberUtil.createOrdersNumber();
        order.setOrderNum(orderNum);
        order.setOrderAmount(interviewer.getCost());
        // 1：待付款  2：已付款 3：已取消 4：交易关闭
        order.setOrderStatus(OrdersStatusEnum.WAIT_PAY.key);
        order.setCreateTime(new Date());
        order.setIntroduction(introduction);
        order.setInterviewerId(interviewerId);
        order.setFinderId(finder.getFid());
        logger.info(order.toString());
        // 插入一条订单
        ordersService.insert(order);
        order = ordersService.getOrderByOrderNum(orderNum);
        //插入一条消息
        Message message = new Message();
        String content = "于" + ActivateCodeUtil.formatDate(new Date()) + "向<b>" + interviewer.getUsername() + "</b>发起面试邀约";
        message.setContent(content);
        message.setMessageUrl("/pay/pay/" + order.getOid());
        message.setUid(finder.getFid());
        message.setUserType(UserEnum.FINDER.key);
        message.setMessageType(MessageEnum.INTERVIEW.key);
        message.setStatusType(StatusTypeEnum.WAIT_PAY.key);
        message.setMessageStatus(MessageStatusEnum.NOT_READ.key);
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
        message.setOid(order.getOid());
        messageService.insert(message);

        return "redirect:/pay/pay/" + order.getOid();
    }

    /**
     * 用户确认订单支付的页面
     *
     * @param oid
     * @param model
     * @return
     */
    @RequestMapping("/pay/{oid}")
    public String payPage(@PathVariable("oid") Integer oid, Model model) {
        Order order = ordersService.getOrderById(oid);
        Finder finder = finderService.getFinderById(order.getFinderId());
        Interviewer interviewer = interviewerService.getInterviewerById(order.getInterviewerId());
        model.addAttribute("finder", finder);
        model.addAttribute("interviewer", interviewer);
        model.addAttribute("order", order);
        return "front/interview/toPay";
    }

    /**
     * 前往支付宝第三方网关进行支付
     *
     * @return
     */
    @RequestMapping(value = "/goAlipay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String goAliPay(Order order) throws AlipayApiException {
        logger.info(order.toString());
        order = ordersService.getOrderByOrderNum(order.getOrderNum());
        logger.info(order.toString());
        if (order == null) {
            return "";
        }
        Interviewer interviewer = interviewerService.getInterviewerById(order.getInterviewerId());
        Finder finder = finderService.getFinderById(order.getFinderId());

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderNum();
        //付款金额，必填
        String total_amount = order.getOrderAmount();
        //订单名称，必填
        String subject = "IT模拟面试" + "(" + finder.getUsername() + "—" + interviewer.getUsername() + ")";
        //商品描述，可空
        String body = "IT模拟面试，面试完成并确认后，金额将直接打进面试官支付宝里；若付款后，未进行面试，退款请联系平台，将在三个工作日退回！";

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }

    /**
     * 支付宝同步通知页面
     * 更新订单、插入一条流水记录、更新消息
     * 付款成功后，跳转到面试环节
     *
     * @return
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public String alipayReturnNotice(HttpServletRequest request, Model model) throws Exception {
        logger.info("支付成功，进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);

        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            // 修改叮当状态，改为 支付成功，已付款;
            Order order = new Order();
            order.setOrderNum(out_trade_no);
            order.setOrderStatus(OrdersStatusEnum.PAID.key);
            order.setPaidAmount(total_amount);
            order.setPaidTime(new Date());
            // 未面试
            order.setIsInterviewed(StatusEnum.NO.key);
            // 未结单
            order.setIsOrdered(StatusEnum.NO.key);
            // 还未评价
            order.setIsEvaluation(StatusEnum.NO.key);
            ordersService.updateOrder(order);
            order = ordersService.getOrderByOrderNum(out_trade_no);
            Interviewer interviewer = interviewerService.getInterviewerById(order.getInterviewerId());
            Finder finder = finderService.getFinderById(order.getFinderId());
            //新增支付流水
            Flow flow = new Flow();
            flow.setFlowNum(trade_no);
            flow.setOrderNum(order.getOrderNum());
            flow.setPaidAmount(order.getPaidAmount());
            flow.setFinderId(order.getFinderId());
            flow.setInterviewerId(order.getInterviewerId());
            flow.setCreateTime(new Date());
            flowService.insert(flow);

            // 更新求职者消息
            logger.info(order.toString());
            Message m = messageService.getMessageByOid(order.getOid(),UserEnum.FINDER.key);
            Message message = new Message();
            message.setMid(m.getMid());
            String content = "于" + ActivateCodeUtil.formatDate(new Date()) + "向<b>" + interviewer.getUsername() + "</b>发起IT模拟面试邀请";
            message.setContent(content);
            message.setMessageUrl("/interview/interview/" + order.getOid());
            message.setStatusType(StatusTypeEnum.WAIT_INTERVIEW.key);
            message.setMessageStatus(MessageStatusEnum.NOT_READ.key);
            message.setUpdateTime(new Date());
            messageService.update(message);
            // 插入面试官一条消息
            content = "<b>" + finder.getUsername() + "</b>在" + ActivateCodeUtil.formatDate(new Date()) + "向您发起IT模拟面试邀请";
            message.setContent(content);
            message.setMessageUrl("/interview/finder/" + order.getOid());
            message.setUid(interviewer.getIid());
            message.setUserType(UserEnum.INTERVIEWER.key);
            message.setMessageType(MessageEnum.INTERVIEW.key);
            message.setOid(order.getOid());
            message.setStatusType(StatusTypeEnum.WAIT_INTERVIEW.key);
            message.setMessageStatus(MessageStatusEnum.NOT_READ.key);
            message.setCreateTime(new Date());
            message.setUpdateTime(new Date());
            messageService.insert(message);

            logger.info("********************** 支付成功(支付宝同步通知) **********************");
            logger.info("* 订单号: {}", out_trade_no);
            logger.info("* 支付宝交易号: {}", trade_no);
            logger.info("* 实付金额: {}", total_amount);
            logger.info("***************************************************************");

            return "redirect:/interview/interview/" + order.getOid();
        } else {
            logger.info("支付, 验签失败...");
            return "";
        }

    }

}
