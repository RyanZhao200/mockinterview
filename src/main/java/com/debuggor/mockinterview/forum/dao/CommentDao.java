package com.debuggor.mockinterview.forum.dao;

import com.debuggor.mockinterview.forum.bean.Comment;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据用户ID，获取其发表过的评论
     *
     * @param uid
     * @param userType
     * @return
     */
    List<Comment> getCommentListByUid(@Param("uid") Integer uid, @Param("userType") String userType);
}
