package com.debuggor.mockinterview.forum.bean;

import lombok.Data;

@Data
public class Type {
    // 帖子类别ID
    private Integer tid;
    // 类别名称
    private String typeName;
    // 帖子顺序
    private Integer orderNo;

}
