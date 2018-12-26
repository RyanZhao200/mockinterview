package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Follower;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.FollowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
}
