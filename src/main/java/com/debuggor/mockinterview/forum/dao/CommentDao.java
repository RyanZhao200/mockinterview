package com.debuggor.mockinterview.forum.dao;

import com.debuggor.mockinterview.forum.bean.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao {
    /**
     * 得到评论列表
     *
     * @param comment
     * @return
     */
    List<Comment> getCommentsList(Comment comment);

    /**
     * 根据帖子ID获得评论
     * 根据parentId获得子评论
     *
     * @param pid      帖子ID
     * @param parentId 父评论ID
     * @return
     */
    List<Comment> getCommentListByPid(@Param("pid") Integer pid, @Param("parentId") Integer parentId);


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

    /**
     * 跟新评论
     *
     * @param comment
     */
    void update(Comment comment);

    /**
     * 根据评论ID，获得评论
     * @param cid
     * @return
     */
    Comment getCommentById(Integer cid);
}
