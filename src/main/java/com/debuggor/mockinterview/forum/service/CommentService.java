package com.debuggor.mockinterview.forum.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.forum.bean.Comment;
import com.debuggor.mockinterview.forum.dao.CommentDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

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
        return comments;
    }
}
