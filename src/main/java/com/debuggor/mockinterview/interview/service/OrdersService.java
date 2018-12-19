package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.interview.bean.Order;
import com.debuggor.mockinterview.interview.dao.OrdersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单的service层
 */
@Service
public class OrdersService {

    @Autowired
    private OrdersDao ordersDao;

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
}
