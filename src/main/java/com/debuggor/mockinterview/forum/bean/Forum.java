package com.debuggor.mockinterview.forum.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Forum {

    /**
     * 帖子ID
     */
    private Integer pid;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 帖子类别ID
     */
    private Integer tid;
    /**
     * 类别名称
     */
    private String typeName;
    /**
     * 创建人ID
     */
    private Integer uid;
    /**
     * 用户类别（1、求职者，2、面试官）
     */
    private String userType;
    /**
     * 创建人姓名
     */
    private String username;
    /**
     * 创建人头像
     */
    private String headUrl;
    /**
     * 帖子状态 1、正常 ， 2、删除
     */
    private String forumStatus;
    /**
     * 浏览量
     */
    private Integer scanCount;
    /**
     * 评论数量
     */
    private Integer commentCount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 跟新时间
     */
    private Date updateTime;
    /**
     * 最近回复时间
     */
    private Date replyTime;
    /**
     * 查询 开始时间
     */
    private Date startTime;
    /**
     * 查询 结束时间
     */
    private Date endTime;
    /**
     * order 排序用
     * null、最新回复;1、最新发表;2、最热;
     */
    private Integer order;

}
