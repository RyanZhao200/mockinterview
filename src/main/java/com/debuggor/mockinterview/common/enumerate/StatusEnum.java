package com.debuggor.mockinterview.common.enumerate;

/**
 * 是否 状态
 */
public enum StatusEnum {
    // 是
    YES("1", "是"),
    // 否
    NO("2", "否"),
    // 正常
    NORMAL("1", "正常"),
    // 删除
    DELETE("2", "删除");

    public final String key;
    public final String value;

    StatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (StatusEnum status : StatusEnum.values()) {
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
