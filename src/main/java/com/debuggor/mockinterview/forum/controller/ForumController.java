package com.debuggor.mockinterview.forum.controller;

import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.bean.Type;
import com.debuggor.mockinterview.forum.service.ForumService;
import com.debuggor.mockinterview.forum.service.ForumTypeService;
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
@RequestMapping("/forum")
public class ForumController {
    Logger logger = LoggerFactory.getLogger(ForumController.class);

    @Autowired
    private ForumService forumService;
    @Autowired
    private ForumTypeService forumTypeService;

    @RequestMapping("")
    public String forumList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                            @RequestParam(required = false, value = "tid") Integer tid, Model model) {
        Forum forum = new Forum();
        if (tid != null) {
            forum.setTid(tid);
        }
        PageInfo pageInfo = forumService.getPostList(forum, pn);
        List<Type> types = forumTypeService.getForumTypeList();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", types);
        return "front/forum/forum";
    }
}
