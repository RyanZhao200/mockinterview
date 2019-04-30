package com.debuggor.mockinterview.common.enumerate;

/**
 * 聊天的消息状态
 * 发送者：（1、已发送，2、已删除）
 * 接收者：（2、已删除，3、已经读，4、未接收）
 */
public enum ChatMessageStatusEnum {
    // 已发送
    SEND("1", "已发送"),
    // 已删除
    DELETE("2", "已删除"),
    //已经读
    READED("3", "已经读"),
    //未接收
    NOT_ACCEPT("4", "未接收");

    public final String key;
    public final String value;

    ChatMessageStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (ChatMessageStatusEnum status : ChatMessageStatusEnum.values()) {
            if (status.getKey().equals(key)) {
                return status.value;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
