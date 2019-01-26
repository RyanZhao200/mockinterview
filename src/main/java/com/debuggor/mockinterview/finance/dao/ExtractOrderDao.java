package com.debuggor.mockinterview.finance.dao;

import com.debuggor.mockinterview.finance.bean.ExtractOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtractOrderDao {
    /**
     * 插入一条提现申请订单
     *
     * @param extractOrder
     */
    void insert(ExtractOrder extractOrder);

    /**
     * 管理员更新订单
     *
     * @param eoid
     */
    void update(Integer eoid);

    /**
     * 根据订单ID得到订单的信息
     *
     * @param eoid
     * @return
     */
    ExtractOrder getExtractOrderById(Integer eoid);

    /**
     * 根据面试官ID，获取最近的一次提交申请
     * 待审核
     *
     * @param iid
     * @return
     */
    ExtractOrder getLastExtractOrderByUid(Integer iid);

    /**
     * 获取面试官提现订单申请列表
     *
     * @param extractOrder
     */
    List<ExtractOrder> getExtractOrders(ExtractOrder extractOrder);
}
