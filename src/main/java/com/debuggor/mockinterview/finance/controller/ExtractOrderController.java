package com.debuggor.mockinterview.finance.controller;

import com.debuggor.mockinterview.common.enumerate.ExtractOrderEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.finance.bean.Amount;
import com.debuggor.mockinterview.finance.bean.ExtractOrder;
import com.debuggor.mockinterview.finance.service.AmountService;
import com.debuggor.mockinterview.finance.service.ExtractOrderService;
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
     *
     * @param extractOrder
     * @return
     */
    @RequestMapping("/submitExtractOrder")
    public String extractOrderSubmit(ExtractOrder extractOrder) {
        logger.info("---------------------------------");
        return "";
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
}
