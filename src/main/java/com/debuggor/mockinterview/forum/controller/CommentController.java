package com.debuggor.mockinterview.forum.controller;

import com.debuggor.mockinterview.common.enumerate.StatusEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.forum.bean.Comment;
import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.service.CommentService;
import com.debuggor.mockinterview.forum.service.ForumService;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 论坛评论controller
 */
@Controller
@RequestMapping("/forum")
public class CommentController {
    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;
    @Autowired
    private ForumService forumService;

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
        comment.setCommentStatus(StatusEnum.NORMAL.key);
        commentService.insert(comment);
        // 更新帖子的评论数量
        Forum forum = forumService.getForumById(comment.getPid());
        if (forum != null) {
            Forum f = new Forum();
            f.setPid(forum.getPid());
            f.setCommentCount(forum.getCommentCount() + 1);
            f.setReplyTime(new Date());
            forumService.update(f);
        }
        return "redirect:/forum/" + comment.getPid();
    }
}
