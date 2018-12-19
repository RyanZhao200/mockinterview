package com.debuggor.mockinterview.interview.bean;

import java.util.Date;

/**
 * 流水表
 */
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

    @Override
    public String toString() {
        return "Flow{" +
                "fid=" + fid +
                ", flowNum='" + flowNum + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", paidAmount='" + paidAmount + '\'' +
                ", finderId=" + finderId +
                ", interviewerId=" + interviewerId +
                ", createTime=" + createTime +
                '}';
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFlowNum() {
        return flowNum;
    }

    public void setFlowNum(String flowNum) {
        this.flowNum = flowNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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
}
