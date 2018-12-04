package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.bean.Type;
import com.debuggor.mockinterview.forum.service.ForumTypeService;
import com.debuggor.mockinterview.forum.service.ForumService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/forum")
public class AdminForumController {
    Logger logger = LoggerFactory.getLogger(AdminForumController.class);

    @Autowired
    private ForumService postService;
    @Autowired
    private ForumTypeService forumTypeService;

    @RequestMapping("/postList")
    public String postList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                           @RequestParam(required = false, value = "tid") Integer tid,
                           @RequestParam(required = false, value = "username") String username, Model model) {
        Forum forum = new Forum();
        if (tid != null) {
            forum.setTid(tid);
        }
        if (username != null) {
            forum.setUsername(username);
        }
        if (forum != null) {
            logger.info(forum.getTid() + " " + forum.getUsername());
        }
        PageInfo pageInfo = postService.getPostList(forum, pn);
        model.addAttribute("pageInfo", pageInfo);
        logger.info(String.valueOf(pageInfo.getTotal()));
        List<Type> typeList = forumTypeService.getForumTypeList();
        model.addAttribute("types", typeList);
        return "admin/forum/forumList";
    }
}
