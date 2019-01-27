package com.debuggor.mockinterview.finance.dao;

import com.debuggor.mockinterview.finance.bean.RechargeRecording;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 充值记录表（求职者）
 */
@Repository
public interface RechargeRecordingDao {
    /**
     * 插入一条充值记录
     *
     * @param rechargeRecording
     */
    void insert(RechargeRecording rechargeRecording);

    /**
     * 根据用户ID，得到用户的充值记录
     *
     * @param finderId 用户ID
     * @return
     */
    List<RechargeRecording> getRechargeRecordingsByUid(Integer finderId);

    /**
     * 根据充值ID，得到充值记录
     *
     * @param rrid 充值表ID
     * @return
     */
    RechargeRecording getRechargeRecordingByid(Integer rrid);

    /**
     * 充值记录列表
     *
     * @return
     */
    List<RechargeRecording> getRechargeRecordings();

}
