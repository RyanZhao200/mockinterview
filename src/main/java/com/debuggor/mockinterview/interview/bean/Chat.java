package com.debuggor.mockinterview.interview.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.Date;

/**
 * 消息的实体
 */

@Data
public class Chat {
    /**
     * 消息ID
     */
    private Integer cid;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 发送人ID
     */
    private Integer sendUid;
    /**
     * 发送者类别 1、求职者，2、面试官
     */
    private String sendType;
    /**
     * 接受者
     */
    private Integer acceptUid;
    /**
     * 接受者类别
     */
    private String acceptType;
    /**
     * 消息状态：是否已经删除；目前没考虑用到这一字段
     */
    private String messageStatus;
    /**
     * 发送时间
     */
    private Date sendTime;

    public Chat() {
    }

    public Chat(String message, Integer sendUid, String sendType, Integer acceptUid, String acceptType, Date sendTime) {
        this.message = message;
        this.sendUid = sendUid;
        this.sendType = sendType;
        this.acceptUid = acceptUid;
        this.acceptType = acceptType;
        this.sendTime = sendTime;
    }

    public Chat(Integer sendUid, String sendType, Integer acceptUid, String acceptType) {
        this.sendUid = sendUid;
        this.sendType = sendType;
        this.acceptUid = acceptUid;
        this.acceptType = acceptType;
    }

    public static String jsonStr(String message, Integer sendUid, String sendType, Integer acceptUid, String acceptType, Date sendTime) {
        return JSON.toJSONString(new Chat(message, sendUid, sendType, acceptUid, acceptType, sendTime));
    }
}
