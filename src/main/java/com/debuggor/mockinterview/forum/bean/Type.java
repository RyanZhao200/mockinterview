package com.debuggor.mockinterview.forum.bean;

import lombok.Data;

/**
 * 帖子类别
 */

@Data
public class Type {
    /**
     * 帖子类别ID
     */
    private Integer tid;
    /**
     * 类别名称
     */
    private String typeName;
    /**
     * 帖子顺序
     */
    private Integer orderNo;
    /**
     * 每个类别对应的帖子数量
     */
    private Integer postNum;

}
