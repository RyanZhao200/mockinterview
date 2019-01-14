package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Flow;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流水Dao
 */
@Repository
public interface FlowDao {
    /**
     * 插入一条流水记录
     *
     * @param flow
     */
    void insert(Flow flow);

    /**
     * 根据面试官的ID，获取面试官面试的数量
     *
     * @param iid
     * @return
     */
    Integer getFlowNumByIid(Integer iid);

    /**
     * 得到所有的流水；求职者已经付款的订单
     *
     * @param flow
     * @return
     */
    List<Flow> getFlowList(Flow flow);
}
