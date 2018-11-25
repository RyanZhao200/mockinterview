package com.debuggor.mockinterview.interview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InterviewController {
    Logger logger = LoggerFactory.getLogger(InterviewController.class);

    @RequestMapping("/")
    public String index() {
        return "front/index";
    }
}
