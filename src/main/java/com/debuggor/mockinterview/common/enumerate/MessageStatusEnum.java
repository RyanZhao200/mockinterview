package com.debuggor.mockinterview.common.enumerate;

/**
 * 消息状态（1、未读，2、已读，3、已删）
 */
public enum MessageStatusEnum {
    // 未读
    NOT_READ("1", "未读"),
    // 已读
    READ("2", "已读"),
    // 已删
    DELETE("3", "已删");

    public final String key;
    public final String value;

    MessageStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (MessageStatusEnum status : MessageStatusEnum.values()) {
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
