package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.bean.vo.InterviewerVo;
import com.debuggor.mockinterview.common.service.InterviewTypeService;
import com.debuggor.mockinterview.interview.bean.Type;
import com.debuggor.mockinterview.interview.service.InterviewerService;
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
    @Autowired
    private InterviewerService interviewerService;

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) {
        // 获取面试官类别
        List<Type> interviewTypeList = interviewTypeService.getInterviewTypeList();
        model.addAttribute("parentList", interviewTypeList);
        // 获取首页展示的面试官
        List<InterviewerVo> interviewerVoList = interviewerService.getInterviewerIndexList();
        model.addAttribute("voList", interviewerVoList);
        return "front/index";
    }
}
