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
                ", isInterviewed='" + isInterviewed + '\'' +
                ", isOrdered='" + isOrdered + '\'' +
                ", isEvaluation='" + isEvaluation + '\'' +
                ", evaluationId=" + evaluationId +
                ", createTime=" + createTime +
                ", paidTime=" + paidTime +
                ", orderedTime=" + orderedTime +
                '}';
    }

    public String getFinderName() {
        return finderName;
    }

    public void setFinderName(String finderName) {
        this.finderName = finderName;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public String getIsInterviewed() {
        return isInterviewed;
    }

    public void setIsInterviewed(String isInterviewed) {
        this.isInterviewed = isInterviewed;
    }

    public String getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(String isOrdered) {
        this.isOrdered = isOrdered;
    }

    public String getIsEvaluation() {
        return isEvaluation;
    }

    public void setIsEvaluation(String isEvaluation) {
        this.isEvaluation = isEvaluation;
    }

    public Integer getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Integer evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Date getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(Date orderedTime) {
        this.orderedTime = orderedTime;
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
