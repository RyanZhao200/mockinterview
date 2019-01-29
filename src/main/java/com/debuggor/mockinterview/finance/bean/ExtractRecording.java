package com.debuggor.mockinterview.finance.bean;

import lombok.Data;

import java.util.Date;

/**
 * 提现记录表（面试官）
 * 管理员同意后才能提现
 */
@Data
public class ExtractRecording {
    /**
     * ID
     */
    private Integer erid;
    /**
     * 提现单号
     */
    private String extractNum;
    /**
     * 支付宝交易号
     */
    private String tradeNum;
    /**
     * 提现金额
     */
    private Float amount;
    /**
     * 面试官ID
     */
    private Integer interviewerId;
    /**
     * 面试官姓名
     */
    private String username;
    /**
     * 创建时间
     */
    private Date createTime;
}
