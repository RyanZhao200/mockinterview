package com.debuggor.mockinterview.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forum")
public class ForumController {

    @RequestMapping("")
    public String forumList(){
        return "front/forum/forum";
    }
}
