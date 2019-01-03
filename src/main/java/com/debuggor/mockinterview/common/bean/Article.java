package com.debuggor.mockinterview.common.bean;

import lombok.Data;

import java.util.Date;
@Data
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

}
