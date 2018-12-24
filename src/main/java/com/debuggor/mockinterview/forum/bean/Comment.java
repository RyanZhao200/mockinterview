package com.debuggor.mockinterview.forum.bean;

import java.util.Date;

public class Comment {

    // 帖子ID
    private Integer cid;
    // 评论内容
    private String comment;
    // 帖子ID
    private Integer pid;
    // 用户ID
    private Integer uid;
    // 用户类别（1、求职者，2、面试官）
    private String userType;
    // 评论人姓名
    private String username;
    //
    private String headUrl;
    // 评论时间
    private Date commentTime;
    // 父ID
    private Integer parentId;

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", comment='" + comment + '\'' +
                ", pid=" + pid +
                ", uid=" + uid +
                ", userType='" + userType + '\'' +
                ", username='" + username + '\'' +
                ", commentTime=" + commentTime +
                ", parentId=" + parentId +
                '}';
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
