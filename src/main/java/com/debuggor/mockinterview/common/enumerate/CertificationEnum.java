package com.debuggor.mockinterview.common.enumerate;

/**
 * 认证的状态
 * （1、认证成功，2、认证失败，3、待审核）
 */
public enum CertificationEnum {
    // 认证成功
    SUCCESS("1", "认证成功"),
    // 认证失败
    FAILURE("2", "认证失败"),
    //待审核
    WAIT_REVIEW("3", "待审核");

    public final String key;
    public final String value;

    CertificationEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (CertificationEnum status : CertificationEnum.values()) {
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
