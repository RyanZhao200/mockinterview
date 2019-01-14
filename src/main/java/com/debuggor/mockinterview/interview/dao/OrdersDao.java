package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 根据求职者ID，找到求职者的订单记录
     *
     * @param fid
     * @return
     */
    List<Order> getOrderByFinderId(Integer fid);

    /**
     * 得到所有订单信息
     *
     * @param order
     * @return
     */
    List<Order> getOrderList(Order order);

}
