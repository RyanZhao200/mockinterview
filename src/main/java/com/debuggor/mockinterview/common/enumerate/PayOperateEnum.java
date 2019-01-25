package com.debuggor.mockinterview.common.enumerate;

/***
 * 用户对账户的操作方式
 * 1、充值，2、提现
 */
public enum PayOperateEnum {
    // 充值
    RECHARGE("1", "充值"),
    // 提现
    EXTRACT("2", "提现");

    public final String key;
    public final String value;

    PayOperateEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (PayOperateEnum status : PayOperateEnum.values()) {
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
