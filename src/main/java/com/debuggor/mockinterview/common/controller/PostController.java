package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.forum.bean.Post;
import com.debuggor.mockinterview.forum.service.PostService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/forum")
public class PostController {
    Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @RequestMapping("/postList")
    public String postList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                           Post post, Model model) {
        PageInfo pageInfo = postService.getPostList(post, pn);
        model.addAttribute("pageInfo", pageInfo);
        logger.info(String.valueOf(pageInfo.getTotal()));
        return "admin/forum/postList";
    }
}
