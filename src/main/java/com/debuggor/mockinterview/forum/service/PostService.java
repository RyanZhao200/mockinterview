package com.debuggor.mockinterview.forum.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.forum.bean.Post;
import com.debuggor.mockinterview.forum.dao.PostDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostDao postDao;

    public PageInfo getPostList(Post post, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Post> posts = postDao.getPostList(post);
        PageInfo pageInfo = new PageInfo<>(posts, PageConstant.Navigate_Pages);
        return pageInfo;
    }
}
