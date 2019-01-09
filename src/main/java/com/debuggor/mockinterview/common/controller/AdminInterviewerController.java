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
@RequestMapping("/admin/user")
public class AdminInterviewerController {
    Logger logger = LoggerFactory.getLogger(AdminInterviewerController.class);

    @Autowired
    private InterviewService interviewService;

    /**
     * 后台面试官列表
     *
     * @param pn
     * @param interviewer
     * @param model
     * @return
     */
    @RequestMapping("/interviewList")
    public String toInterviewerList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                    Interviewer interviewer, Model model) {
        PageInfo pageInfo = interviewService.getInterviewerList(interviewer, pn);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/user/interviewList";
    }

}
