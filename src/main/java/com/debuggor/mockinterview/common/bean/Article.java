package com.debuggor.mockinterview.common.bean;

import java.util.Date;

public class Article {
    // 文章ID
    private Integer aid;
    // 标题
    private String title;
    // 内容
    private String content;
    // 用户ID
    private Integer uid;
    // 创建时间
    private Date createTime;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
