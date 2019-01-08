package com.debuggor.mockinterview.forum.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.bean.Type;
import com.debuggor.mockinterview.forum.dao.ForumDao;
import com.debuggor.mockinterview.forum.dao.ForumTypeDao;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.FinderDao;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ForumService {
    @Autowired
    private ForumDao forumDao;
    @Autowired
    private FinderDao finderDao;
    @Autowired
    private InterviewerDao interviewerDao;
    @Autowired
    private ForumTypeDao forumTypeDao;

    /**
     * 帖子列表
     *
     * @param forum
     * @param pn
     * @return
     */
    public PageInfo getPostList(Forum forum, Integer pn) {
        if (forum != null) {
            if (forum.getUsername() != null || !"".equals(forum.getUsername())) {
                Finder finder = finderDao.getFinderByUserName(forum.getUsername());
                if (finder != null) {
                    forum.setUid(finder.getFid());
                }
            }
        }
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Forum> posts = forumDao.getPostList(forum);
        PageInfo<Forum> pageInfo = new PageInfo<>(posts, PageConstant.Navigate_Pages);
        // 用户的信息：之前在sql语句实现，后加面试官进去，sql语句不好写，故在service层实现
        PageInfo pageInfoNew = pageInfo;
        List<Forum> forums = new ArrayList<>();
        for (Forum post : pageInfo.getList()) {
            if (post.getUserType().equals(UserEnum.FINDER.key)) {
                Finder finder = finderDao.getFinderById(post.getUid());
                post.setUsername(finder.getUsername());
                post.setHeadUrl(finder.getHeadUrl());
            }
            if (post.getUserType().equals(UserEnum.INTERVIEWER.key)) {
                Interviewer interviewer = interviewerDao.getInterviewerById(post.getUid());
                post.setUsername(interviewer.getUsername());
                post.setHeadUrl(interviewer.getHeadUrl());
            }
            forums.add(post);
        }
        pageInfoNew.setList(forums);
        return pageInfoNew;
    }

    /**
     * 根据ID，获得帖子的详情
     *
     * @param pid
     * @return
     */
    public Forum getForumById(Integer pid) {
        if (pid == null) {
            return null;
        }
        Forum post = forumDao.getForumById(pid);
        if (post == null) {
            return null;
        }
        if (post.getUserType().equals(UserEnum.FINDER.key)) {
            Finder finder = finderDao.getFinderById(post.getUid());
            post.setUsername(finder.getUsername());
            post.setHeadUrl(finder.getHeadUrl());
        }
        if (post.getUserType().equals(UserEnum.INTERVIEWER.key)) {
            Interviewer interviewer = interviewerDao.getInterviewerById(post.getUid());
            post.setUsername(interviewer.getUsername());
            post.setHeadUrl(interviewer.getHeadUrl());
        }
        return post;
    }

    /**
     * 添加帖子，返回帖子ID
     * 更新帖子对应栏目的数量
     *
     * @param forum
     * @return
     */
    public Integer insertForum(Forum forum) {
        //评论数量0
        forum.setCommentCount(0);
        // 浏览数量0
        forum.setScanCount(0);
        Date date = new Date();
        //创建时间
        forum.setCreateTime(date);
        //更新时间
        forum.setUpdateTime(date);
        //最近回复时间
        forum.setReplyTime(date);
        Integer pid = forumDao.insertForum(forum);
        // 更新栏目帖子数量
        Type type = forumTypeDao.getTypeById(forum.getTid());
        if (type != null) {
            type.setPostNum(type.getPostNum() + 1);
            forumTypeDao.updateType(type);
        }
        return pid;
    }

    /**
     * 根据用户的ID，获取其发表的帖子
     *
     * @param pn       分页页数
     * @param uid      用户ID
     * @param userType 用户类别
     */
    public PageInfo<Forum> getForumsByUid(Integer pn, Integer uid, String userType) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes_Small);
        List<Forum> forums = forumDao.getForumsByUId(uid, userType);
        PageInfo<Forum> pageInfo = new PageInfo<>(forums, PageConstant.Navigate_Pages);
        return pageInfo;
    }

    /**
     * 更新帖子
     */
    public void update(Forum forum) {
        forum.setUpdateTime(new Date());
        forumDao.update(forum);
    }

    /**
     * 近期热帖 15条
     */
    public List<Forum> getRecentHotPosts() {
        List<Forum> hotPosts = forumDao.getRecentHotPosts();
        return hotPosts;
    }
}
