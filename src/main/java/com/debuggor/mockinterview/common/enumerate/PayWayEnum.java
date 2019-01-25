package com.debuggor.mockinterview.common.enumerate;

/***
 * 支付方式
 * 1、支付宝，2、微信支付
 */
public enum PayWayEnum {
    // 支付宝
    ALIPAY("1", "支付宝"),
    // 微信
    WXPAY("2", "微信");

    public final String key;
    public final String value;

    PayWayEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (PayWayEnum status : PayWayEnum.values()) {
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
