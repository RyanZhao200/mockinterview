package com.debuggor.mockinterview.forum.dao;

import com.debuggor.mockinterview.forum.bean.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {

    /**
     * 根据帖子ID获得评论
     *
     * @param pid 帖子ID
     * @return
     */
    List<Comment> getCommentListByPid(Integer pid);

    /**
     * 插入一条评论
     *
     * @param comment
     */
    void insert(Comment comment);
}
