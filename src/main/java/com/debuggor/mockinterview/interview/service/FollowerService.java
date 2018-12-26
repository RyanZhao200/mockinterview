package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.enumerate.FollowStatusEnum;
import com.debuggor.mockinterview.interview.bean.Follower;
import com.debuggor.mockinterview.interview.dao.FollowerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 关注表的service
 */
@Service
public class FollowerService {
    @Autowired
    private FollowerDao followerDao;

    /**
     * 插入一条关注记录
     *
     * @param follower
     */
    public void addFollower(Follower follower) {
        if (follower == null) {
            return;
        }
        follower.setFollowStatus(FollowStatusEnum.FOLLOW.key);
        follower.setFollowTime(new Date());
        followerDao.insert(follower);
    }

    /**
     * 取消关注
     *
     * @param follower
     */
    public void unFollower(Follower follower) {
        follower.setUnfollowTime(new Date());
        follower.setFollowStatus(FollowStatusEnum.UNFOLLOW.key);
        followerDao.update(follower);
    }

    /**
     * 根据用户的ID，获取关注我的人
     *
     * @param uid
     * @param userType
     */
    public List<Follower> getFollowersByUid(Integer uid, String userType) {
        if (uid == null || userType == null) {
            return null;
        }
        List<Follower> followers = followerDao.getFollowersByUid(uid, userType);
        return followers;
    }

    /**
     * 根据用户ID，获取我关注的人
     *
     * @param uid
     * @param userType
     */
    public List<Follower> getFollowingByUid(Integer uid, String userType) {
        if (uid == null || userType == null) {
            return null;
        }
        List<Follower> followers = followerDao.getFollowingByUid(uid, userType);
        return followers;
    }

    /**
     * 是否关注
     *
     * @param follower
     * @return
     */
    public Boolean isFollowed(Follower follower) {
        if (follower == null) {
            return false;
        }
        follower.setFollowStatus(FollowStatusEnum.FOLLOW.key);
        follower = followerDao.isFollowedByUUid(follower);
        if (follower == null) {
            return false;
        }
        return true;
    }
}
