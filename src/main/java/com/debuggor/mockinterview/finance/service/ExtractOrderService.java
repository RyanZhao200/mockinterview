package com.debuggor.mockinterview.finance.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.common.enumerate.ExtractOrderEnum;
import com.debuggor.mockinterview.finance.bean.ExtractOrder;
import com.debuggor.mockinterview.finance.dao.ExtractOrderDao;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExtractOrderService {

    @Autowired
    private ExtractOrderDao extractOrderDao;
    @Autowired
    private InterviewerDao interviewerDao;

    /**
     * 面试官发起提现申请，插入一条数据到数据库中
     */
    public void insert(ExtractOrder extractOrder) {
        extractOrder.setOrderStatus(ExtractOrderEnum.WAIT_REVIEW.key);
        extractOrder.setCreateTime(new Date());
        extractOrderDao.insert(extractOrder);
    }

    /**
     * 根据ID获得提现订单详情
     *
     * @param eid
     * @return
     */
    public ExtractOrder getExtractOrderById(Integer eid) {
        ExtractOrder extractOrder = extractOrderDao.getExtractOrderById(eid);
        return extractOrder;
    }

    /**
     * 根据面试官ID，获得面试官提交的最近的一次提现申请
     * 待审核
     *
     * @param iid
     * @return
     */
    public ExtractOrder getLastExtractOrderByUid(Integer iid) {
        if (iid == null) {
            return null;
        }
        ExtractOrder extractOrder = extractOrderDao.getLastExtractOrderByUid(iid);
        return extractOrder;
    }

    /**
     * 获取提现订单列表
     *
     * @param extractOrder
     * @param pn
     */
    public PageInfo<ExtractOrder> extractOrderList(ExtractOrder extractOrder, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<ExtractOrder> extractOrders = extractOrderDao.getExtractOrders(extractOrder);
        PageInfo<ExtractOrder> pageInfo = new PageInfo<>(extractOrders, PageConstant.Navigate_Pages);
        return pageInfo;
    }
}
