package com.debuggor.mockinterview.finance.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.finance.bean.RechargeRecording;
import com.debuggor.mockinterview.finance.dao.RechargeRecordingDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RechargeRecordingService {

    @Autowired
    private RechargeRecordingDao rechargeRecordingDao;

    /**
     * 插入一条充值记录
     *
     * @param rechargeRecording
     */
    public void insert(RechargeRecording rechargeRecording) {
        rechargeRecording.setCreateTime(new Date());
        rechargeRecordingDao.insert(rechargeRecording);
    }

    /**
     * 得到求职者的充值记录
     *
     * @param finderId 求职者ID
     * @param pn       页数
     * @return
     */
    public PageInfo<RechargeRecording> getRechargeRecordingsByUid(Integer finderId, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<RechargeRecording> recordings = rechargeRecordingDao.getRechargeRecordingsByUid(finderId);
        PageInfo<RechargeRecording> pageInfo = new PageInfo<>(recordings, PageConstant.Navigate_Pages);
        return pageInfo;
    }
}
