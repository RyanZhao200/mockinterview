package com.debuggor.mockinterview.forum.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Comment {

    /**
     * 帖子ID
     */
    private Integer cid;
    /**
     * 评论内容
     */
    private String comment;
    /**
     * 帖子ID
     */
    private Integer pid;
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 用户类别（1、求职者，2、面试官）
     */
    private String userType;
    /**
     * 评论人姓名
     */
    private String username;
    /**
     * 评论人头像
     */
    private String headUrl;
    /**
     * 评论状态 1、正常，2、删除
     */
    private String commentStatus;
    /**
     * 评论时间
     */
    private Date commentTime;
    /**
     * 父ID
     */
    private Integer parentId;
    /**
     * 评论对应的帖子
     */
    private Forum forum;
    /**
     * 子评论:只支持一级评论
     */
    private List<Comment> sonComments;
    /**
     * 查询 开始时间
     */
    private String startTime;
    /**
     * 查询 结束时间
     */
    private String endTime;
}
