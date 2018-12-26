package com.debuggor.mockinterview.interview.bean;

import java.util.Date;

/**
 * 关注表
 */
public class Follower {
    /**
     * 关注表ID
     */
    private Integer fid;
    /**
     * 被关注者id,姓名，头像，类别（1、求职者，2、面试官）
     */
    private Integer followersUid;
    private String followersUsername;
    private String followersHeadUrl;
    private String followersType;
    /**
     * 关注者id，姓名，头像，类别（1、求职者，2、面试官）
     */
    private Integer followingUid;
    private String followingUsername;
    private String followingHeadUrl;
    private String followingType;
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

    @Override
    public String toString() {
        return "Follower{" +
                "fid=" + fid +
                ", followersUid=" + followersUid +
                ", followersUsername='" + followersUsername + '\'' +
                ", followersHeadUrl='" + followersHeadUrl + '\'' +
                ", followersType='" + followersType + '\'' +
                ", followingUid=" + followingUid +
                ", followingUsername='" + followingUsername + '\'' +
                ", followingHeadUrl='" + followingHeadUrl + '\'' +
                ", followingType='" + followingType + '\'' +
                ", followStatus='" + followStatus + '\'' +
                ", followTime=" + followTime +
                ", unfollowTime=" + unfollowTime +
                '}';
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getFollowersUid() {
        return followersUid;
    }

    public void setFollowersUid(Integer followersUid) {
        this.followersUid = followersUid;
    }

    public String getFollowersUsername() {
        return followersUsername;
    }

    public void setFollowersUsername(String followersUsername) {
        this.followersUsername = followersUsername;
    }

    public String getFollowersHeadUrl() {
        return followersHeadUrl;
    }

    public void setFollowersHeadUrl(String followersHeadUrl) {
        this.followersHeadUrl = followersHeadUrl;
    }

    public String getFollowersType() {
        return followersType;
    }

    public void setFollowersType(String followersType) {
        this.followersType = followersType;
    }

    public Integer getFollowingUid() {
        return followingUid;
    }

    public void setFollowingUid(Integer followingUid) {
        this.followingUid = followingUid;
    }

    public String getFollowingUsername() {
        return followingUsername;
    }

    public void setFollowingUsername(String followingUsername) {
        this.followingUsername = followingUsername;
    }

    public String getFollowingHeadUrl() {
        return followingHeadUrl;
    }

    public void setFollowingHeadUrl(String followingHeadUrl) {
        this.followingHeadUrl = followingHeadUrl;
    }

    public String getFollowingType() {
        return followingType;
    }

    public void setFollowingType(String followingType) {
        this.followingType = followingType;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public Date getFollowTime() {
        return followTime;
    }

    public void setFollowTime(Date followTime) {
        this.followTime = followTime;
    }

    public Date getUnfollowTime() {
        return unfollowTime;
    }

    public void setUnfollowTime(Date unfollowTime) {
        this.unfollowTime = unfollowTime;
    }
}
