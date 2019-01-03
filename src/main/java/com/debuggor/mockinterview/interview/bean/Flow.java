package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.Date;

/**
 * 流水表
 */
@Data
public class Flow {
    /**
     * 流水ID
     */
    private Integer fid;
    /**
     * 流水号
     */
    private String flowNum;
    /**
     * 订单号
     */
    private String orderNum;
    /**
     * 支付金额
     */
    private String paidAmount;
    /**
     * 求职者ID（付款人ID）
     */
    private Integer finderId;
    /**
     * 面试官ID（准收款人ID）
     */
    private Integer interviewerId;
    /**
     * 创建时间
     */
    private Date createTime;
}
