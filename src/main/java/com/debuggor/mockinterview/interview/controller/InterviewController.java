package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.bean.vo.InterviewerVo;
import com.debuggor.mockinterview.common.service.InterviewTypeService;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.bean.Type;
import com.debuggor.mockinterview.interview.service.InterviewerService;
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

    /**
     * 面试官列表（查询）
     *
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                       @RequestParam(required = false, value = "tid") Integer tid,
                       Interviewer interviewer, Model model) {
        // 获取面试官类别
        List<Type> interviewTypeList = interviewTypeService.getInterviewTypeList();
        model.addAttribute("parentList", interviewTypeList);
        // 获取所有的面试官信息 列表
        if (tid != null) {
            interviewer.setTid(tid);
            model.addAttribute("tid", tid);
        }
        PageInfo interviewerList = interviewerService.getInterviewerList(interviewer, pn);
        logger.info("总共" + String.valueOf(interviewerList.getTotal()) + "条记录");
        model.addAttribute("pageInfo", interviewerList);
        return "front/interview/list";
    }
}
