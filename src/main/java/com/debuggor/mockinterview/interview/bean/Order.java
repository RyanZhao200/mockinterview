package com.debuggor.mockinterview.interview.bean;

import java.util.Date;

/**
 * 订单表
 */
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
     * 面试官ID
     */
    private Integer interviewerId;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date paidTime;

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", orderNum='" + orderNum + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", paidAmount='" + paidAmount + '\'' +
                ", introduction='" + introduction + '\'' +
                ", resumeUrl='" + resumeUrl + '\'' +
                ", finderId=" + finderId +
                ", interviewerId=" + interviewerId +
                ", createTime=" + createTime +
                ", paidTime=" + paidTime +
                '}';
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Integer getFinderId() {
        return finderId;
    }

    public void setFinderId(Integer finderId) {
        this.finderId = finderId;
    }

    public Integer getInterviewerId() {
        return interviewerId;
    }

    public void setInterviewerId(Integer interviewerId) {
        this.interviewerId = interviewerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(Date paidTime) {
        this.paidTime = paidTime;
    }
}
