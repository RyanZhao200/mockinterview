package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Flow;
import org.springframework.stereotype.Repository;

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
}
