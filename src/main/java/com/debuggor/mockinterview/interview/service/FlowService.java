package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.interview.bean.Flow;
import com.debuggor.mockinterview.interview.dao.FlowDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 得到所有的流水记录并分页：付费，退款
     *
     * @param flow
     * @param pn
     * @return
     */
    public PageInfo getFlowList(Flow flow, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Flow> flows = flowDao.getFlowList(flow);
        PageInfo pageInfo = new PageInfo<>(flows, PageConstant.Navigate_Pages);
        return pageInfo;
    }
}
