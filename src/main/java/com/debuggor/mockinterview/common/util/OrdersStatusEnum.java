package com.debuggor.mockinterview.common.util;

/**
 * 订单状态
 */
public enum OrdersStatusEnum {
    // 代付款
    WAIT_PAY("1", "待付款"),
    // 已付款
    PAID("2", "已付款"),
    // 已取消
    CANCELED("3", "已取消"),
    // 超时未支付, 交易关闭
    CLOSED("4", "交易关闭");

    public final String key;
    public final String value;

    OrdersStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (OrdersStatusEnum status : OrdersStatusEnum.values()) {
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
