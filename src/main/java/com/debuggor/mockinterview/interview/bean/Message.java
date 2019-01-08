package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.Date;

/**
 * 消息通知实体（论坛、面试）
 */
@Data
public class Message {
    /**
     * 消息ID
     */
    private Integer mid;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息链接
     */
    private String messageUrl;
    /**
     * 用户ID
     */
    private Integer uid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户类别（1、求职者，2、面试官）
     */
    private String userType;
    /**
     * 消息类别（1：论坛；2：面试）
     */
    private String messageType;
    /**
     * 订单ID
     */
    private Integer oid;
    /**
     * 状态类别（面试：1、待付款，2、待面试，3、待结单，4、待评价，5、面试结束；论坛：1、帖子，2、评论）
     */
    private String statusType;
    /**
     * 消息状态（1、未读，2、已读，3、已删）
     */
    private String messageStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最近更新时间
     */
    private Date updateTime;

}
