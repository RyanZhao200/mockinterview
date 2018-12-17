package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.bean.vo.InterviewerVo;
import com.debuggor.mockinterview.common.constant.UserConstant;
import com.debuggor.mockinterview.common.service.InterviewTypeService;
import com.debuggor.mockinterview.common.service.QiniuService;
import com.debuggor.mockinterview.common.util.RtcRoomManager;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.bean.Type;
import com.debuggor.mockinterview.interview.service.InterviewService;
import com.github.pagehelper.PageInfo;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class InterviewController {
    Logger logger = LoggerFactory.getLogger(InterviewController.class);

    @Autowired
    private InterviewTypeService interviewTypeService;
    @Autowired
    private InterviewService interviewService;
    @Autowired
    private QiniuService qiniuService;

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
        List<InterviewerVo> interviewerVoList = interviewService.getInterviewerIndexList();
        model.addAttribute("voList", interviewerVoList);
        return "front/index";
    }

    /**
     * 面试官列表（查询）
     *
     * @param model
     * @return
     */
    @RequestMapping("/interviewer")
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
        PageInfo interviewerList = interviewService.getInterviewerList(interviewer, pn);
        logger.info("总共" + String.valueOf(interviewerList.getTotal()) + "条记录");
        model.addAttribute("pageInfo", interviewerList);
        return "front/interview/list";
    }


    /**
     * 面试官详情页
     *
     * @return
     */
    @RequestMapping("/interviewer/{iid}")
    public String interview(@PathVariable("iid") Integer iid, Model model) {
        Interviewer interviewer = interviewService.getInterviewerById(iid);
        model.addAttribute("interviewer", interviewer);
        return "/front/interview/detail";
    }

    /**
     * 面试官向求职者发起视频聊天
     *
     * @return
     */
    @RequestMapping("/publish")
    public String interviewerToFinder(@RequestParam(required = false, value = "fid") Integer fid,
                                      HttpSession session, Model model) throws Exception {
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");

//        String roomToken = qiniuService.getRoomToken(interviewer.getIid(), fid, UserConstant.Interviewer_Type);

        String roomToken = qiniuService.getRoomToken(93, 93, UserConstant.Interviewer_Type);
        model.addAttribute("roomToken", roomToken);
        return "/front/interview/publish";
    }

    /**
     * 面试官向求职者发起视频聊天
     *
     * @return
     */
    @RequestMapping("/subscribe")
    public String finderToInterviewer(@RequestParam(required = false, value = "iid") Integer iid,
                                      HttpSession session, Model model) throws Exception {
        Finder finder = (Finder) session.getAttribute("finder");
//        String roomToken = qiniuService.getRoomToken(iid, finder.getFid(), UserConstant.Finder_Type);
        String roomToken = qiniuService.getRoomToken(93, 93, UserConstant.Finder_Type);
        model.addAttribute("roomToken", roomToken);
        return "/front/interview/subscribe";
    }
}
