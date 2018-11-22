package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.InterviewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/interview")
public class InterviewerController {

    @Autowired
    private InterviewerService interviewerService;


    @RequestMapping("/interviewList")
    public String toInterviewerList(Interviewer interviewer,Model model){
        List<Interviewer> interviewerList = interviewerService.getInterviewerList(interviewer);
        model.addAttribute("interviews",interviewerList);
        return "admin/interView/interviewList";
    }

}
