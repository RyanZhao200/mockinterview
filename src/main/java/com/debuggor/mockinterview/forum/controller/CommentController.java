package com.debuggor.mockinterview.forum.controller;

import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.forum.bean.Comment;
import com.debuggor.mockinterview.forum.dao.CommentDao;
import com.debuggor.mockinterview.forum.service.CommentService;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 论坛评论controller
 */
@Controller
@RequestMapping("/forum")
public class CommentController {
    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    /**
     * 插入一条评论
     *
     * @param comment
     * @return
     */
    @RequestMapping("/insertComment")
    public String insert(Comment comment, HttpSession session) {
        Finder finder = (Finder) session.getAttribute("finder");
        if (finder != null) {
            comment.setUserType(UserEnum.FINDER.key);
            comment.setUid(finder.getFid());
            logger.info(finder.getUsername() + "插入一条评论");
        }
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        if (interviewer != null) {
            comment.setUserType(UserEnum.INTERVIEWER.key);
            comment.setUid(interviewer.getIid());
            logger.info(interviewer.getUsername() + "插入一条评论");
        }
        commentService.insert(comment);
        return "redirect:" + comment.getPid();
    }
}
