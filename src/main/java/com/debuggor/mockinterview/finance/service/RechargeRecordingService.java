package com.debuggor.mockinterview.finance.service;

import com.debuggor.mockinterview.finance.bean.RechargeRecording;
import com.debuggor.mockinterview.finance.dao.RechargeRecordingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
