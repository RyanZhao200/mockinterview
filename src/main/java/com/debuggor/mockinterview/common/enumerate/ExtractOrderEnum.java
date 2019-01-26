package com.debuggor.mockinterview.common.enumerate;

/**
 * 面试官提现订单状态
 */
public enum ExtractOrderEnum {
    // 代付款
    WAIT_REVIEW("1", "待审核"),
    // 已付款
    REVIEW_PASS("2", "审核通过"),
    // 已取消
    REVIEW_NO_PASS("3", "审核不通过");

    public final String key;
    public final String value;

    ExtractOrderEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (ExtractOrderEnum status : ExtractOrderEnum.values()) {
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
