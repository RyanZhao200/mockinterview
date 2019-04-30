package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ChatSession {
    /**
     * ID
     */
    private Integer cid;
    /**
     * 消息内容
     */
    private String messgae;
    /**
     * 发送者ID
     */
    private Integer sendUserId;
    /**
     * 发送者类别
     */
    private String sendUserType;
    /**
     * 消息对发送者的状态（1、已发送，2、已删除）
     */
    private String sendStatus;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 接收者ID
     */
    private Integer acceptUserId;
    /**
     * 接收者类别
     */
    private String acceptUserType;
    /**
     * 接收者消息状态（2、已删除，3、已经读，4、未接收）
     */
    private String acceptStatus;
    /**
     * 接收时间
     */
    private Date acceptTime;
}
