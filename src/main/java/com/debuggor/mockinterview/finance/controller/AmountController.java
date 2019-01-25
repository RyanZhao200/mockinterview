package com.debuggor.mockinterview.finance.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.debuggor.mockinterview.common.config.AlipayConfig;
import com.debuggor.mockinterview.common.enumerate.PayOperateEnum;
import com.debuggor.mockinterview.common.enumerate.PayWayEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.common.util.AlipayUtil;
import com.debuggor.mockinterview.common.util.OrdersNumberUtil;
import com.debuggor.mockinterview.finance.bean.Amount;
import com.debuggor.mockinterview.finance.bean.RechargeRecording;
import com.debuggor.mockinterview.finance.service.AmountService;
import com.debuggor.mockinterview.finance.service.RechargeRecordingService;
import com.debuggor.mockinterview.interview.bean.Finder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 用户总金额controller
 */
@Controller
@RequestMapping("/amount")
public class AmountController {

    private Logger logger = LoggerFactory.getLogger(AmountController.class);

    @Autowired
    private AmountService amountService;
    @Autowired
    private RechargeRecordingService rechargeService;

    /**
     * 前往第三方网关进行支付
     * 根据用户的选择，选择哪种支付方式
     *
     * @param amount  充值金额，只允许充值整数金额
     * @param payWay  支付方式
     * @param session
     * @return
     */
    @RequestMapping(value = "/goRechargePay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String goRechargePay(@RequestParam("amount") Integer amount,
                                @RequestParam("payWay") String payWay,
                                HttpSession session) throws AlipayApiException {
        Finder finder = (Finder) session.getAttribute("finder");
        String result = null;
        if (PayWayEnum.ALIPAY.key.equals(payWay)) {
            // 支付宝支付
            result = goAliPay(amount, finder);
        } else if (PayWayEnum.WXPAY.key.equals(payWay)) {
            // 微信支付，后续完善
        }
        return result;
    }

    /**
     * 前往支付宝第三方网关进行支付
     * 求职者前往支付宝充值页面
     *
     * @return
     */
    private String goAliPay(Integer amount, Finder finder) throws AlipayApiException {

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url_recharge);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url_recharge);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        // 订单号 30位 单一无二
        String out_trade_no = OrdersNumberUtil.createOrdersNumber();
        //付款金额，必填
        Integer total_amount = amount;
        //订单名称，必填
        String subject = "IT模拟面试充值";
        //商品描述，可空
        String body = "IT模拟面试充值，可用于面试付费";

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
     * 求职者充值成功 ，修改求职者的账户金额，插入一条充值记录；
     * 给用户提示，跳转到用户的余额页面
     *
     * @return
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public String alipayReturnNotice(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        Finder finder = (Finder) session.getAttribute("finder");
        Map<String, String[]> requestParams = request.getParameterMap();
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = AlipayUtil.getAlipayParams(requestParams);
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

            //修改用户的账户金额；充值
            Amount amount = new Amount();
            amount.setUserId(finder.getFid());
            amount.setUserType(UserEnum.FINDER.key);
            amount.setAmount(new BigDecimal(total_amount));
            String result = amountService.update(amount, PayOperateEnum.RECHARGE.key);
            logger.info("充值结果: {}", result);
            // 插入一条充值记录
            RechargeRecording recharge = new RechargeRecording();
            recharge.setAmount(new BigDecimal(total_amount).floatValue());
            recharge.setFinderId(finder.getFid());
            recharge.setRechargeNum(out_trade_no);
            recharge.setTradeNum(trade_no);
            rechargeService.insert(recharge);

            logger.info("********************** 支付成功(支付宝同步通知) **********************");
            logger.info("* 订单号: {}", out_trade_no);
            logger.info("* 支付宝交易号: {}", trade_no);
            logger.info("* 实付金额: {}", total_amount);
            logger.info("***************************************************************");

            return "redirect:/finder/amountPage";
        } else {
            logger.info("支付, 验签失败...");
            return "";
        }
    }


}
