package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.Date;

/**
 * 订单表
 */
@Data
public class Order {
    /**
     * 订单ID
     */
    private Integer oid;
    /**
     * 订单号
     */
    private String orderNum;
    /**
     * 订单状态 1：待付款  2：已付款
     */
    private String orderStatus;
    /**
     * 订单金额
     */
    private String orderAmount;
    /**
     * 实际支付金额
     */
    private String paidAmount;
    /**
     * 求职者个人介绍
     */
    private String introduction;
    /**
     * 简历链接
     */
    private String resumeUrl;
    /**
     * 求职者ID
     */
    private Integer finderId;
    /**
     * 求职者用户名
     */
    private String finderName;
    /**
     * 面试官ID
     */
    private Integer interviewerId;
    /**
     * 面试官用户名
     */
    private String interviewerName;
    /**
     * 是否面试（1：是,2：否）（面试官跟新）
     */
    private String isInterviewed;
    /**
     * 是否结单（1：是,2：否）(求职者跟新)
     */
    private String isOrdered;
    /**
     * 是否已经评价（1：是,2：否）
     */
    private String isEvaluation;
    /**
     * 评论表单ID
     */
    private Integer evaluationId;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date paidTime;
    /**
     * 结单时间
     */
    private Date orderedTime;

}
