package com.debuggor.mockinterview.finance.bean;

import lombok.Data;

import java.util.Date;

/**
 * 提现记录表（面试官）
 */
@Data
public class ExtractRecording {
    /**
     * ID
     */
    private Integer erid;
    /**
     * 提现金额
     */
    private Float amount;
    /**
     * 面试官ID
     */
    private Integer interviewerId;
    /**
     * 创建时间
     */
    private Date createTime;
}
