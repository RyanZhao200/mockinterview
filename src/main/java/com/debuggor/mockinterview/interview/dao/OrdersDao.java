package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Order;
import org.springframework.stereotype.Repository;

/**
 * 订单的dao层
 */
@Repository
public interface OrdersDao {
    /**
     * 插入一条订单
     *
     * @param order
     */
    void insert(Order order);

    /**
     * 更新订单
     *
     * @param order
     */
    void update(Order order);

    /**
     * 根据订单号获取订单信息
     *
     * @param orderNum
     * @return
     */
    Order getOrderByOrderNum(String orderNum);

    /**
     * 根据订单ID，获取订单信息
     *
     * @param oid
     * @return
     */
    Order getOrderById(Integer oid);
}
