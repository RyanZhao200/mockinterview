package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.interview.bean.Flow;
import com.debuggor.mockinterview.interview.dao.FlowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流水的service层
 */
@Service
public class FlowService {

    @Autowired
    private FlowDao flowDao;

    /**
     * 插入一条流水记录
     *
     * @param flow
     */
    public void insert(Flow flow) {
        if (flow != null) {
            flowDao.insert(flow);
        }
    }
}
