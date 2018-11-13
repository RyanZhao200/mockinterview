package com.debuggor.mockinterview.forum.bean;

public class Type {
    // 帖子类别ID
    private Integer tid;
    // 类别名称
    private String typeName;
    // 帖子顺序
    private Integer orderNo;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
