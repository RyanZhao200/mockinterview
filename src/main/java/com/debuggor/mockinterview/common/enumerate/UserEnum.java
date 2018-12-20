package com.debuggor.mockinterview.common.enumerate;

/**
 * 用户类型
 * 0、管理员，1、求职者，2、面试官
 */
public enum UserEnum {
    // 管理员
    ADMIN("0", "管理员"),
    // 求职者
    FINDER("1", "求职者"),
    // 面试官
    INTERVIEWER("2", "面试官");

    public final String key;
    public final String value;

    UserEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (UserEnum status : UserEnum.values()) {
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
