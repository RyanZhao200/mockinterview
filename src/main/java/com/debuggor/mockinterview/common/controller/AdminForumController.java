package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.util.TimeUtil;
import com.debuggor.mockinterview.forum.bean.Comment;
import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.bean.Type;
import com.debuggor.mockinterview.forum.service.CommentService;
import com.debuggor.mockinterview.forum.service.ForumTypeService;
import com.debuggor.mockinterview.forum.service.ForumService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/forum")
public class AdminForumController {
    private Logger logger = LoggerFactory.getLogger(AdminForumController.class);
    private UtilController utilController = new UtilController();

    @Autowired
    private ForumService postService;
    @Autowired
    private ForumTypeService forumTypeService;
    @Autowired
    private CommentService commentService;

    /**
     * 帖子列表
     *
     * @param pn
     * @param tid
     * @param model
     * @return
     */
    @RequestMapping("/postList")
    public String postList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                           @RequestParam(required = false, defaultValue = "0", value = "tid") Integer tid,
                           @RequestParam(required = false, value = "startTime") String startTime,
                           @RequestParam(required = false, value = "endTime") String endTime,
                           Model model) throws ParseException {
        Forum forum = new Forum();
        forum.setTid(tid);
        forum.setStartTime(startTime);
        forum.setEndTime(endTime);

        PageInfo pageInfo = postService.getPostList(forum, pn);
        model.addAttribute("pageInfo", pageInfo);
        List<Type> typeList = forumTypeService.getForumTypeList();
        model.addAttribute("types", typeList);
        // 为分页做辅助
        model.addAttribute("tid", tid);
        model.addAttribute("username", forum.getUsername());
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "admin/forum/forumList";
    }

    /**
     * 得到评论的列表
     *
     * @param pn        第几页
     * @param pid       帖子ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param model
     * @return
     */
    @RequestMapping("/commentList")
    public String getCommentsList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                  @RequestParam(required = false, value = "pid") Integer pid,
                                  @RequestParam(required = false, value = "startTime") String startTime,
                                  @RequestParam(required = false, value = "endTime") String endTime,
                                  Model model) {
        Comment comment = new Comment();
        comment.setPid(pid);
        comment.setStartTime(startTime);
        comment.setEndTime(endTime);
        PageInfo pageInfo = commentService.getCommentsList(comment, pn);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pid", pid);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "admin/forum/commentList";
    }

    /**
     * 转到跟新帖子的页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("/editPost/{pid}")
    public String editPost(@PathVariable("pid") Integer pid, Model model) {
        Forum post = postService.getForumById(pid);
        model.addAttribute("post", post);
        return "admin/forum/editPost";
    }

    /**
     * 执行更新帖子
     *
     * @param forum
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/editPostAction")
    public void editPostAction(Forum forum, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Admin admin = utilController.getAdminFromSession(request);
        logger.info(admin.getUsername() + "于" + TimeUtil.format(new Date()) + "修改帖子:" + forum.getTitle() + ";状态：" + forum.getForumStatus());
        if (forum != null) {
            postService.update(forum);
        }
        StringBuilder str = new StringBuilder();
        str.append("<script>");
        str.append(" window.opener.location.href = window.opener.location.href; window.close();");
        str.append("</script>");
        response.getWriter().write(str.toString());
    }

    /**
     * 转到编辑评论页面
     *
     * @param cid
     * @param model
     * @return
     */
    @RequestMapping("/editComment/{cid}")
    public String editComment(@PathVariable("cid") Integer cid, Model model) {
        Comment comment = commentService.getCommentById(cid);
        model.addAttribute("comment", comment);
        return "admin/forum/editComment";
    }

    /**
     * 提交修改评论
     *
     * @return
     */
    @RequestMapping("/editCommentAction")
    public void editCommentAction(Comment comment, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Admin admin = utilController.getAdminFromSession(request);
        logger.info(admin.getUsername() + "于" + TimeUtil.format(new Date()) + "修改评论" + comment.toString());
        if (comment != null) {
            commentService.updateComment(comment);
        }
        StringBuilder str = new StringBuilder();
        str.append("<script>");
        str.append(" window.opener.location.href = window.opener.location.href; window.close();");
        str.append("</script>");
        response.getWriter().write(str.toString());
    }
}
