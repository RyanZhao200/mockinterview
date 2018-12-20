package com.debuggor.mockinterview.common.enumerate;

/**
 * 消息状态类别（面试：1、待付款，2、待面试，3、待结单，4、待评价，5、面试结束；论坛：1、帖子，2、评论）
 */
public enum StatusTypeEnum {

    // 待付款
    WAIT_PAY("1", "待付款"),
    // 待面试
    WAIT_INTERVIEW("2", "待面试"),
    //待结单
    WAIT_ORDERED("3", "待结单"),
    //
    WAIT_COMMENT("4","待评价"),
    //面试结束
    OVER_INTERVIEW("5", "面试结束"),
    //论坛：1、帖子
    POST("1", "帖子"),
    //2、评论
    COMMENT("2", "评论");


    public final String key;
    public final String value;

    StatusTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (StatusTypeEnum status : StatusTypeEnum.values()) {
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
