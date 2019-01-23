package com.debuggor.mockinterview.finance.bean;

import lombok.Data;

import java.util.Date;

/**
 * 充值记录表（求职者）
 */
@Data
public class RechargeRecording {
    /**
     * ID
     */
    private Integer rrid;
    /**
     * 充值金额
     */
    private Float amount;
    /**
     * 求职者ID
     */
    private Integer finderId;
    /**
     * 创建时间
     */
    private Date createTime;
}
