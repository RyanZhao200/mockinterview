package com.debuggor.mockinterview.forum.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.forum.bean.Comment;
import com.debuggor.mockinterview.forum.dao.CommentDao;
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
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private FinderDao finderDao;
    @Autowired
    private InterviewerDao interviewerDao;

    /**
     * 获取帖子的评论 有分页功能
     *
     * @param pid 帖子ID
     * @param pn  页数
     * @return
     */
    public PageInfo getCommentListByPid(Integer pid, Integer pn) {
        if (pid == null) {
            return null;
        }
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Comment> comments = commentDao.getCommentListByPid(pid);
        PageInfo pageInfo = new PageInfo<>(comments, PageConstant.Navigate_Pages);
        return pageInfo;
    }

    /**
     * 获取帖子的评论 不分页
     *
     * @param pid
     * @return
     */
    public List<Comment> getCommentListByPid(Integer pid) {
        if (pid == null) {
            return null;
        }
        List<Comment> comments = commentDao.getCommentListByPid(pid);
        List<Comment> list = new ArrayList<>();
        for (Comment c : comments) {
            if (UserEnum.FINDER.key.equals(c.getUserType())) {
                Finder finder = finderDao.getFinderById(c.getUid());
                c.setUsername(finder.getUsername());
                c.setHeadUrl(finder.getHeadUrl());
            }
            if (UserEnum.INTERVIEWER.key.equals(c.getUserType())) {
                Interviewer interviewer = interviewerDao.getInterviewerById(c.getUid());
                c.setUsername(interviewer.getUsername());
                c.setHeadUrl(interviewer.getHeadUrl());
            }
            list.add(c);
        }
        return list;
    }

    /**
     * 插入一条评论
     */
    public void insert(Comment comment) {
        if (comment != null) {
            comment.setCommentTime(new Date());
        }
        commentDao.insert(comment);
    }
}
