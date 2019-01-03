package com.debuggor.mockinterview.forum.dao;

import com.debuggor.mockinterview.forum.bean.Forum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumDao {

    /**
     * 帖子列表
     *
     * @param post
     * @return
     */
    List<Forum> getPostList(Forum post);

    /**
     * 根据ID获得帖子详情
     *
     * @param pid
     * @return
     */
    Forum getForumById(Integer pid);

    /**
     * 添加帖子，返回帖子ID
     *
     * @param forum
     * @return
     */
    Integer insertForum(Forum forum);

    /**
     * 根据用户的ID，获取其发表的帖子
     *
     * @param uid
     * @param userType
     * @return
     */
    List<Forum> getForumsByUId(@Param("uid") Integer uid, @Param("userType") String userType);

    /**
     * 更新帖子
     *
     * @param forum
     */
    void update(Forum forum);

    /**
     * 获得近期的热帖
     *
     * @return
     */
    List<Forum> getRecentHotPosts();
}
