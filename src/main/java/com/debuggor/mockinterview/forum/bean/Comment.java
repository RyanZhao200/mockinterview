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
    // 评论人姓名
    private String username;
    // 评论时间
    private Date commentTime;
    // 父ID
    private Integer parentId;

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
