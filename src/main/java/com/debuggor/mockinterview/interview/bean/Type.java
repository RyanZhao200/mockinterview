package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.List;

@Data
public class Type {

    /**
     * 分类ID
     */
    private Integer tid;
    /**
     * 栏目名称
     */
    private String typeName;
    /**
     * 栏目顺序
     */
    private Integer orderNo;
    /**
     * 父栏目ID
     */
    private Integer parentId;
    /**
     * 父栏目名称
     */
    private String parentName;
    /**
     * 子栏目
     */
    private List<Type> childTypes;

}
