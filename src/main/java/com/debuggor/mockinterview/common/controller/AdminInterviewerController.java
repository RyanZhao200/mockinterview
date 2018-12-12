package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.InterviewService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/interview")
public class AdminInterviewerController {
    Logger logger = LoggerFactory.getLogger(AdminInterviewerController.class);

    @Autowired
    private InterviewService interviewService;


    @RequestMapping("/interviewList")
    public String toInterviewerList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                    Interviewer interviewer, Model model) {
        PageInfo pageInfo = interviewService.getInterviewerList(interviewer, pn);
        model.addAttribute("pageInfo", pageInfo);
        logger.info(String.valueOf(pageInfo.getTotal()));
        return "admin/interView/interviewList";
    }

}
