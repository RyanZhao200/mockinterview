package com.debuggor.mockinterview.common.enumerate;

/**
 * 消息类别 1、论坛消息，2、面试的消息
 */
public enum MessageEnum {
    // 论坛消息
    FORUM("1", "论坛"),
    // 面试
    INTERVIEW("2", "面试");

    public final String key;
    public final String value;

    MessageEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (MessageEnum status : MessageEnum.values()) {
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
