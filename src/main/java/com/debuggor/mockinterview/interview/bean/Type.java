package com.debuggor.mockinterview.interview.bean;

public class Type {

    // 分类ID
    private Integer tid;
    // 栏目名称
    private String typeName;
    // 栏目顺序
    private Integer orderNo;
    // 父栏目ID
    private Integer parentId;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
