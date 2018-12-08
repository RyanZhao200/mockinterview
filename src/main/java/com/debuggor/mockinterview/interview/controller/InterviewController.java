package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.service.InterviewTypeService;
import com.debuggor.mockinterview.interview.bean.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class InterviewController {
    Logger logger = LoggerFactory.getLogger(InterviewController.class);

    @Autowired
    private InterviewTypeService interviewTypeService;

    @RequestMapping("/")
    public String index(Model model) {
        List<Type> interviewTypeList = interviewTypeService.getInterviewTypeList();
        model.addAttribute("parentList", interviewTypeList);
        return "front/index";
    }
}
