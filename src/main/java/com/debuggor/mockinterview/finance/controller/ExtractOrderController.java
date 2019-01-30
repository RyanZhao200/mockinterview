package com.debuggor.mockinterview.finance.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.debuggor.mockinterview.common.enumerate.ExtractOrderEnum;
import com.debuggor.mockinterview.common.enumerate.PayOperateEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.common.util.OrdersNumberUtil;
import com.debuggor.mockinterview.common.util.TimeUtil;
import com.debuggor.mockinterview.finance.bean.Amount;
import com.debuggor.mockinterview.finance.bean.ExtractOrder;
import com.debuggor.mockinterview.finance.bean.ExtractRecording;
import com.debuggor.mockinterview.finance.service.AmountService;
import com.debuggor.mockinterview.finance.service.ExtractOrderService;
import com.debuggor.mockinterview.finance.service.ExtractRecordingService;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.InterviewerService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 面试官提现
 */
@Controller
@RequestMapping("/extractOrder")
public class ExtractOrderController {

    private Logger logger = LoggerFactory.getLogger(ExtractOrderController.class);

    @Autowired
    private ExtractOrderService extractOrderService;
    @Autowired
    private InterviewerService interviewerService;
    @Autowired
    private AmountService amountService;
    @Autowired
    private ExtractRecordingService extractRecordingService;

    /**
     * 面试官提现订单申请
     *
     * @param amount
     * @param account
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/submitOrder")
    public String toExtractOrder(@RequestParam("amount") Float amount,
                                 @RequestParam("account") String account,
                                 HttpSession session, Model model) {
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        model.addAttribute("interviewer", interviewer);
        ExtractOrder extractOrder = new ExtractOrder();
        extractOrder.setInterviewerId(interviewer.getIid());
        extractOrder.setAliAccount(account);
        extractOrder.setAmount(amount);
        extractOrderService.insert(extractOrder);
        return "redirect:/interviewer/amountPage";
    }

    /**
     * 面试官提现申请，带审核
     *
     * @param pn
     * @param model
     * @return
     */
    @RequestMapping("/waitReview")
    public String waitExtractOrderList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                       Model model) {
        ExtractOrder extractOrder = new ExtractOrder();
        extractOrder.setOrderStatus(ExtractOrderEnum.WAIT_REVIEW.key);
        PageInfo<ExtractOrder> pageInfo = extractOrderService.extractOrderList(extractOrder, pn);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/finance/waitReview";
    }

    /**
     * 面试官提现订单详情；管理员审核是否通过
     *
     * @param eoid
     * @param model
     * @return
     */
    @RequestMapping("/waitOrderDetail/{eoid}")
    public String waitExtractOrderDetail(@PathVariable("eoid") Integer eoid, Model model) {
        ExtractOrder extractOrder = extractOrderService.getExtractOrderById(eoid);
        if (extractOrder == null) {
            return "redirect:/admin";
        }
        Interviewer interviewer = interviewerService.getInterviewerById(extractOrder.getInterviewerId());
        Amount amount = new Amount();
        amount.setUserId(extractOrder.getInterviewerId());
        amount.setUserType(UserEnum.INTERVIEWER.key);
        amount = amountService.getUserAmount(amount);
        model.addAttribute("amount", amount);
        model.addAttribute("extractOrder", extractOrder);
        model.addAttribute("interviewer", interviewer);
        return "admin/finance/waitReviewDetail";
    }

    /**
     * 提交提现申请结果
     * 如果审核不通过，更新订单状态
     * 审核通过，转账到面试官提供给的支付宝账户
     *
     * @param extractOrder
     * @return
     */
    @RequestMapping("/submitExtractOrder")
    public String extractOrderSubmit(ExtractOrder extractOrder) throws AlipayApiException {
        if (extractOrder == null || extractOrder.getOrderStatus() == null || extractOrder.getEoid() == null) {
            return "redirect:/admin";
        }
        logger.info(TimeUtil.format(new Date()) + ":" + extractOrder.toString());
        if (ExtractOrderEnum.REVIEW_NO_PASS.key.equals(extractOrder.getOrderStatus())) {
            //审核不通过
            logger.info("审核不通过！");
            extractOrderService.update(extractOrder);
        } else if (ExtractOrderEnum.REVIEW_PASS.key.equals(extractOrder.getOrderStatus())) {
            // 审核通过，验证提现金额是否大于本地账户余额；转账到面试官的账户
            logger.info("审核通过！");
            ExtractOrder orderById = extractOrderService.getExtractOrderById(extractOrder.getEoid());
            Amount amount = new Amount();
            amount.setUserId(orderById.getInterviewerId());
            amount.setUserType(UserEnum.INTERVIEWER.key);
            amount.setAmount(new BigDecimal(orderById.getAmount()));
            Boolean canExtract = amountService.canExtract(amount);
            if (canExtract) {
                // 可以提现
                Boolean result = alipayFundTransToaccountTransfer(extractOrder);
                if (result) {
                    // 支付宝调用成功
                    extractOrderService.update(extractOrder);
                } else {
                    //支付宝调用错误，不可以提现，
                    extractOrder.setOrderStatus(ExtractOrderEnum.REVIEW_NO_PASS.key);
                    extractOrder.setReviewInfo("支付宝调用错误，请稍后再试或者联系商家");
                    extractOrderService.update(extractOrder);
                }
            } else {
                // 不可以提现，
                extractOrder.setOrderStatus(ExtractOrderEnum.REVIEW_NO_PASS.key);
                extractOrder.setReviewInfo("账户余额不足");
                extractOrderService.update(extractOrder);
            }
        }
        return "redirect:/extractOrder/extractOrderDetail/" + extractOrder.getEoid();
    }

    /**
     * 支付宝商家转账到单个用户
     *
     * @param extractOrder
     * @return
     * @throws AlipayApiException
     */
    public Boolean alipayFundTransToaccountTransfer(ExtractOrder extractOrder) throws AlipayApiException {
        extractOrder = extractOrderService.getExtractOrderById(extractOrder.getEoid());
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", "app_id", "your private_key", "json", "GBK", "alipay_public_key", "RSA2");
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();

        Float amount = extractOrder.getAmount();
        String aliAccount = extractOrder.getAliAccount();
        // 订单号 30位 单一无二
        String orderNum = OrdersNumberUtil.createOrdersNumber();
        //ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。
        String payeeType = "ALIPAY_LOGONID";
        String payerShowName = "IT模拟面试退款";
        String remark = "退款提示，有任何问题请联系平台";

        request.setBizContent("{" +
                "\"out_biz_no\":\"" + orderNum + "\"," +
                "\"payee_type\":\"" + payeeType + "\"," +
                "\"payee_account\":\"" + aliAccount + "\"," +
                "\"amount\":\"" + amount + "\"," +
                "\"payer_show_name\":\"" + payerShowName + "\"," +
                "\"remark\":\"" + remark + "\"" +
                "  }");

        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            // 调用成功，修改面试官本地金额
            Amount a = new Amount();
            a.setUserId(extractOrder.getInterviewerId());
            a.setUserType(UserEnum.INTERVIEWER.key);
            a.setAmount(new BigDecimal(amount));
            String result = amountService.update(a, PayOperateEnum.EXTRACT.key);
            logger.info("*****支付宝调用成功******");
            //插入一条提现记录
            // 商户转账唯一订单号：发起转账来源方定义的转账单据号。请求时对应的参数，原样返回。
            String outBizNo = response.getOutBizNo();
            // 支付宝转账单据号，成功一定返回，失败可能不返回也可能返回。
            String orderId = response.getOrderId();
            ExtractRecording extractRecording = new ExtractRecording();
            extractRecording.setAmount(amount);
            extractRecording.setTradeNum(orderId);
            extractRecording.setExtractNum(outBizNo);
            extractRecording.setInterviewerId(extractOrder.getInterviewerId());
            extractRecordingService.insert(extractRecording);

            return true;
        } else {
            // 失败，暂不处理
            logger.info("******支付宝调用失败*****");
            return false;
        }
    }

    /**
     * 面试官提现 审核通过和不通过的订单列表
     *
     * @param pn
     * @param orderStatus 订单状态，审核通过和不通过共用一个页面
     * @param model
     * @return
     */
    @RequestMapping("/extractOrders")
    public String extractOrderList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                   @RequestParam(required = false, defaultValue = "2", value = "orderStatus") String orderStatus,
                                   Model model) {
        ExtractOrder extractOrder = new ExtractOrder();
        extractOrder.setOrderStatus(orderStatus);
        PageInfo<ExtractOrder> pageInfo = extractOrderService.extractOrderList(extractOrder, pn);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("orderStatus", orderStatus);
        return "admin/finance/extractOrders";
    }

    /**
     * 审核通过和审核未通过的详情页面
     *
     * @param eoid
     * @param model
     * @return
     */
    @RequestMapping("/extractOrderDetail/{eoid}")
    public String extractOrderDetail(@PathVariable("eoid") Integer eoid, Model model) {
        ExtractOrder extractOrder = extractOrderService.getExtractOrderById(eoid);
        if (extractOrder == null) {
            return "redirect:/admin";
        }
        Interviewer interviewer = interviewerService.getInterviewerById(extractOrder.getInterviewerId());
        model.addAttribute("extractOrder", extractOrder);
        model.addAttribute("interviewer", interviewer);
        return "admin/finance/extractOrderDetail";
    }
}
