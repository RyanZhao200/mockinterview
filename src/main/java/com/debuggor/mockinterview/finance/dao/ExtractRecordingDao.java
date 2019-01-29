package com.debuggor.mockinterview.finance.dao;

import com.debuggor.mockinterview.finance.bean.ExtractRecording;
import com.debuggor.mockinterview.finance.bean.RechargeRecording;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtractRecordingDao {
    /**
     * 插入一条提现记录
     *
     * @param extractRecording
     */
    void insert(ExtractRecording extractRecording);

    /**
     * 根据用户ID，得到用户的提现记录
     *
     * @param interviewerId 用户ID
     * @return
     */
    List<ExtractRecording> getExtractRecordingsByUid(Integer interviewerId);

    /**
     * 根据充值ID，得到提现记录
     *
     * @param erid 提现表ID
     * @return
     */
    ExtractRecording getExtractRecordingByid(Integer erid);

    /**
     * 得到面试官的提现记录列表
     *
     * @return
     */
    List<ExtractRecording> getExtractRecordings();
}
