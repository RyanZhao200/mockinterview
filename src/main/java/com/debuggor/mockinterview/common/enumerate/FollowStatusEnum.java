package com.debuggor.mockinterview.common.enumerate;

/**
 * 关注状态
 */
public enum FollowStatusEnum {

    // 关注
    FOLLOW("1", "关注"),
    // 取消关注
    UNFOLLOW("2", "取消关注"),
    // 拉黑
    BLACK("3", "拉黑");

    public final String key;
    public final String value;

    FollowStatusEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getName(String key) {
        for (FollowStatusEnum status : FollowStatusEnum.values()) {
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
