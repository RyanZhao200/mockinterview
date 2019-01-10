package com.debuggor.mockinterview.forum.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.common.enumerate.StatusEnum;
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
     * 评论列表
     *
     * @param comment
     * @param pn
     * @return
     */
    public PageInfo getCommentsList(Comment comment, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Comment> comments = commentDao.getCommentsList(comment);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments, PageConstant.Navigate_Pages);
        //  添加评论的其他信息
        List<Comment> list = new ArrayList<>();
        for (Comment c : pageInfo.getList()) {
            Comment commentOtherInfo = getCommentOtherInfo(c);
            list.add(commentOtherInfo);
        }
        pageInfo.setList(list);
        return pageInfo;
    }

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
        List<Comment> comments = commentDao.getCommentListByPid(pid, 0);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments, PageConstant.Navigate_Pages);
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
        // 获得一级评论
        List<Comment> comments = commentDao.getCommentListByPid(pid, 0);
        List<Comment> list = new ArrayList<>();
        for (Comment c : comments) {
            // 获得二级评论
            List<Comment> sonComments = commentDao.getCommentListByPid(pid, c.getCid());
            if (sonComments != null) {
                List<Comment> sonC = new ArrayList<>();
                for (Comment son : sonComments) {
                    Comment comment = getCommentOtherInfo(son);
                    sonC.add(comment);
                }
                sonComments = sonC;
            }
            Comment comment = getCommentOtherInfo(c);
            comment.setSonComments(sonComments);
            list.add(comment);
        }
        return list;
    }

    /**
     * 补充评论的其他信息
     */
    private Comment getCommentOtherInfo(Comment c) {
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
        return c;
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

    /**
     * 根据用户的ID，获取其发表过的评论
     *
     * @param uid
     * @param userType
     * @return
     */
    public PageInfo<Comment> getCommentListByUid(Integer pn, Integer uid, String userType) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes_Small);
        List<Comment> comments = commentDao.getCommentListByUid(uid, userType);
        PageInfo<Comment> pageInfo = new PageInfo<>(comments, PageConstant.Navigate_Pages);
        return pageInfo;
    }

    /**
     * 跟新评论
     *
     * @param comment
     */
    public void updateComment(Comment comment) {
        if (comment != null) {
            commentDao.update(comment);
        }
    }
}
