package com.debuggor.mockinterview.forum.dao;

import com.debuggor.mockinterview.forum.bean.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao {

    /**
     * 帖子列表
     *
     * @param post
     * @return
     */
    List<Post> getPostList(Post post);
}
