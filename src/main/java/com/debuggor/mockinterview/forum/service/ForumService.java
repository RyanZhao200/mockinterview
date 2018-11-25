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

import java.util.List;

@Service
public class ForumService {
    @Autowired
    private ForumDao postDao;
    @Autowired
    private FinderDao finderDao;

    public PageInfo getPostList(Forum forum, Integer pn) {
        if (forum != null) {
            if (forum.getNickname() != null || !"".equals(forum.getNickname())) {
                Finder finder = finderDao.getFinderByNickName(forum.getNickname());
                if (finder != null) {
                    forum.setUid(finder.getFid());
                }
            }
        }
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Forum> posts = postDao.getPostList(forum);
        PageInfo pageInfo = new PageInfo<>(posts, PageConstant.Navigate_Pages);
        return pageInfo;
    }
}
