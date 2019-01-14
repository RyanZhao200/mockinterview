package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.interview.bean.Order;
import com.debuggor.mockinterview.interview.service.OrdersService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {
    private Logger logger = LoggerFactory.getLogger(AdminOrderController.class);

    @Autowired
    private OrdersService ordersService;

    /**
     * @param pn
     * @param order
     * @param model
     * @return
     */
    @RequestMapping("/orderList")
    public String ordersPage(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                             Order order, Model model) {
        PageInfo pageInfo = ordersService.getOrderList(order, pn);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/interview/orderList";
    }
}
