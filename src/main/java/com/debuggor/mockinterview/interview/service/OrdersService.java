package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.bean.Order;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import com.debuggor.mockinterview.interview.dao.OrdersDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单的service层
 */
@Service
public class OrdersService {

    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private InterviewerDao interviewerDao;

    /**
     * 插入一条订单
     *
     * @param order
     * @return
     */
    public void insert(Order order) {
        ordersDao.insert(order);
    }

    /**
     * 根据订单号获取订单详情
     *
     * @param orderNum
     * @return
     */
    public Order getOrderByOrderNum(String orderNum) {
        Order order = ordersDao.getOrderByOrderNum(orderNum);
        return order;
    }

    /**
     * 更新订单号
     *
     * @param order
     */
    public void updateOrder(Order order) {
        ordersDao.update(order);
    }

    /**
     * 根据订单ID获取订单
     *
     * @param oid
     * @return
     */
    public Order getOrderById(Integer oid) {
        Order order = ordersDao.getOrderById(oid);
        return order;
    }

    /**
     * 根据求职者ID，查询出求职者的订单记录
     *
     * @param fid
     * @return
     */
    public List<Order> getOrderByFinderId(Integer fid) {
        List<Order> orders = new ArrayList<>();
        if (fid != null) {
            List<Order> orderList = ordersDao.getOrderByFinderId(fid);
            Interviewer interviewer = null;
            for (Order order : orderList) {
                interviewer = interviewerDao.getInterviewerById(order.getInterviewerId());
                order.setInterviewerName(interviewer.getUsername());
                orders.add(order);
            }
        }
        return orders;
    }

    /**
     * 所有的订单信息
     *
     * @param order
     * @param pn
     * @return
     */
    public PageInfo getOrderList(Order order, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Order> orders = ordersDao.getOrderList(order);
        PageInfo pageInfo = new PageInfo<>(orders, PageConstant.Navigate_Pages);
        return pageInfo;
    }
}
