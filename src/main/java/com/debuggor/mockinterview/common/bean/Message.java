package com.debuggor.mockinterview.common.bean;

import java.util.Date;

/**
 * 消息通知实体（论坛、面试）
 */
public class Message {
    /**
     * 消息ID
     */
    private Integer mid;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息链接
     */
    private String messageUrl;
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户类别（1、求职者，2、面试官）
     */
    private String userType;
    /**
     * 消息类别（1：论坛；2：面试）
     */
    private String messageType;
    /**
     * 订单ID
     */
    private Integer oid;
    /**
     * 状态类别（面试：1、待付款，2、待面试，3、待结单，4、待评价，5、面试结束；论坛：1、帖子，2、评论）
     */
    private String statusType;
    /**
     * 消息状态（1、未读，2、已读，3、已删）
     */
    private String messageStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最近更新时间
     */
    private Date updateTime;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
