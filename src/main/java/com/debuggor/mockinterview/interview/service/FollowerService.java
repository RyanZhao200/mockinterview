package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.common.enumerate.FollowStatusEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Follower;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.FinderDao;
import com.debuggor.mockinterview.interview.dao.FollowerDao;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 关注表的service
 */
@Service
public class FollowerService {
    @Autowired
    private FollowerDao followerDao;
    @Autowired
    private FinderDao finderDao;
    @Autowired
    private InterviewerDao interviewerDao;

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
     * 根据用户ID，获取我关注的人、关注我的人
     * 不分页
     */
    public List<Follower> getFollowByUser(Follower follower) {
        follower.setFollowStatus(FollowStatusEnum.FOLLOW.key);
        List<Follower> followers = followerDao.getFollowByUser(follower);
        List<Follower> list = followersUtils(followers);
        return list;
    }

    /**
     * 获取关注；分页
     *
     * @param follower
     */
    public PageInfo<Follower> getFollowByUserToPages(Integer pn, Follower follower) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Follower> followers = followerDao.getFollowByUser(follower);
        PageInfo<Follower> pageInfo = new PageInfo<>(followers, PageConstant.Navigate_Pages);
        List<Follower> list = followersUtils(pageInfo.getList());
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * followers工具，添加额外的信息
     *
     * @param followers
     * @return
     */
    private List<Follower> followersUtils(List<Follower> followers) {
        if (followers == null) {
            return null;
        }
        List<Follower> list = new ArrayList<>();
        for (Follower f : followers) {
            // 被关注者的信息
            if (UserEnum.FINDER.key.equals(f.getFollowersType())) {
                Finder finder = finderDao.getFinderById(f.getFollowersUid());
                f.setFollowersUsername(finder.getUsername());
                f.setFollowersHeadUrl(finder.getHeadUrl());
                f.setFollowersSignature(finder.getSignature());
            } else if (UserEnum.INTERVIEWER.key.equals(f.getFollowersType())) {
                Interviewer interviewer = interviewerDao.getInterviewerById(f.getFollowersUid());
                f.setFollowersUsername(interviewer.getUsername());
                f.setFollowersHeadUrl(interviewer.getHeadUrl());
                f.setFollowersWorkYear(interviewer.getWorkYear());
            }
            // 关注者的信息
            if (UserEnum.FINDER.key.equals(f.getFollowingType())) {
                Finder finder = finderDao.getFinderById(f.getFollowingUid());
                f.setFollowingUsername(finder.getUsername());
                f.setFollowingHeadUrl(finder.getHeadUrl());
                f.setFollowingSignature(finder.getSignature());
            } else if (UserEnum.INTERVIEWER.key.equals(f.getFollowingType())) {
                Interviewer interviewer = interviewerDao.getInterviewerById(f.getFollowingUid());
                f.setFollowingUsername(interviewer.getUsername());
                f.setFollowingHeadUrl(interviewer.getHeadUrl());
                f.setFollowingWorkYear(interviewer.getWorkYear());
            }
            list.add(f);
        }
        return list;
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
