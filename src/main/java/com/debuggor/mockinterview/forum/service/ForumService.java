package com.debuggor.mockinterview.forum.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.dao.ForumDao;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.dao.FinderDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ForumService {
    @Autowired
    private ForumDao forumDao;
    @Autowired
    private FinderDao finderDao;

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
        PageInfo pageInfo = new PageInfo<>(posts, PageConstant.Navigate_Pages);
        return pageInfo;
    }

    /**
     * 根据ID，获得帖子的详情
     *
     * @param pid
     * @return
     */
    public Forum getForumById(Integer pid) {
        Forum forum = null;
        if (pid != null) {
            forum = forumDao.getForumById(pid);
        }
        return forum;
    }

    /**
     * 添加帖子，返回帖子ID
     *
     * @param forum
     * @return
     */
    public Integer insertForum(Forum forum) {
        forum.setCommentCount(0); //评论数量0
        forum.setScanCount(0); // 浏览数量0
        Date date = new Date(); //
        forum.setCreateTime(date); //创建时间
        forum.setUpdateTime(date); //更新时间
        forum.setReplyTime(date); //最近回复时间
        Integer pid = forumDao.insertForum(forum);
        return pid;
    }
}
