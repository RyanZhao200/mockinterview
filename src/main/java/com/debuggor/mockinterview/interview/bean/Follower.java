package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.Date;

/**
 * 关注表
 */
@Data
public class Follower {
    /**
     * 关注表ID
     */
    private Integer fid;
    /**
     * 被关注者id,姓名，头像，类别（1、求职者，2、面试官）
     * 求职者签名、面试官工作年限
     */
    private Integer followersUid;
    private String followersUsername;
    private String followersHeadUrl;
    private String followersType;
    private String followersSignature;
    private Integer followersWorkYear;
    /**
     * 关注者id，姓名，头像，类别（1、求职者，2、面试官）
     */
    private Integer followingUid;
    private String followingUsername;
    private String followingHeadUrl;
    private String followingType;
    private String followingSignature;
    private Integer followingWorkYear;
    /**
     * 关注的状态（1、关注，2、取消关注，3、拉黑）
     */
    private String followStatus;
    /**
     * 关注时间
     */
    private Date followTime;
    /**
     * 取消关注时间
     */
    private Date unfollowTime;

}
