package com.debuggor.mockinterview.interview.controller;

import com.alibaba.fastjson.JSONObject;
import com.debuggor.mockinterview.common.enumerate.FollowStatusEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Follower;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.FollowerService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 关注controller
 */
@Controller
@RequestMapping("/follower")
public class FollowerController {
    private Logger logger = LoggerFactory.getLogger(FollowerController.class);

    @Autowired
    private FollowerService followerService;

    /***
     * 关注
     * @return
     */
    @RequestMapping("/follow")
    public @ResponseBody
    String follower(Follower follower, HttpSession session) {
        logger.info(follower.toString());
        Finder finder = (Finder) session.getAttribute("finder");
        if (finder != null) {
            follower.setFollowingUid(finder.getFid());
            follower.setFollowingType(UserEnum.FINDER.key);
        }
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        if (interviewer != null) {
            follower.setFollowingUid(interviewer.getIid());
            follower.setFollowingType(UserEnum.INTERVIEWER.key);
        }
        followerService.addFollower(follower);
        return "success";
    }

    /**
     * 取消关注
     *
     * @return
     */
    @RequestMapping("/unfollow")
    public @ResponseBody
    String unfollower(Follower follower, HttpSession session) {
        logger.info(follower.toString());
        Finder finder = (Finder) session.getAttribute("finder");
        if (finder != null) {
            follower.setFollowingUid(finder.getFid());
            follower.setFollowingType(UserEnum.FINDER.key);
        }
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        if (interviewer != null) {
            follower.setFollowingUid(interviewer.getIid());
            follower.setFollowingType(UserEnum.INTERVIEWER.key);
        }
        followerService.unFollower(follower);
        return "success";
    }

    /**
     * 跳转到面试官关注的人的页面
     *
     * @param iid
     * @return
     */
    @RequestMapping("/ivFollowers")
    public String toInterviewerFollowers(@RequestParam(value = "iid") Integer iid, Model model) {
        model.addAttribute("iid", iid);
        return "front/user/interviewer/followers";
    }

    /**
     * 面试官关注的人
     */
    @RequestMapping("/interviewerFollowers")
    public @ResponseBody
    Map<String, Object> interviewerFollowers(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                             Integer iid) {
        Follower follower = new Follower();
        follower.setFollowingUid(iid);
        follower.setFollowingType(UserEnum.INTERVIEWER.key);
        follower.setFollowStatus(FollowStatusEnum.FOLLOW.key);
        follower.setFollowersType(UserEnum.INTERVIEWER.key);
        follower.setFollowStatus(FollowStatusEnum.FOLLOW.key);

        PageInfo<Follower> followInterviewer = followerService.getFollowByUserToPages(pn, follower);
        follower.setFollowersType(UserEnum.FINDER.key);
        PageInfo<Follower> followFinder = followerService.getFollowByUserToPages(pn, follower);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("fip", followInterviewer);
        returnMap.put("ffp", followFinder);
        return returnMap;
    }

    /**
     * 面试官的粉丝页面
     *
     * @param iid
     * @return
     */
    @RequestMapping("/ivFollowing")
    public String toInterviewerFollowing(@RequestParam(value = "iid") Integer iid) {
        return "";
    }

    /**
     * 面试官的粉丝
     */
    @RequestMapping("/interviewerFollowing")
    public @ResponseBody
    String interviewerFollowing(@RequestParam(value = "iid") Integer iid) {
        Follower follower = new Follower();
        JSONObject jo = new JSONObject();
        return "";
    }
}
