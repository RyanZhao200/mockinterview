package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.bean.Type;
import com.debuggor.mockinterview.forum.service.ForumTypeService;
import com.debuggor.mockinterview.forum.service.ForumService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/forum")
public class AdminForumController {
    Logger logger = LoggerFactory.getLogger(AdminForumController.class);

    @Autowired
    private ForumService postService;
    @Autowired
    private ForumTypeService forumTypeService;

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
        logger.info(String.valueOf(pageInfo.getTotal()));
        List<Type> typeList = forumTypeService.getForumTypeList();
        model.addAttribute("types", typeList);
        // 为分页做辅助
        model.addAttribute("tid", forum.getTid());
        model.addAttribute("username", forum.getUsername());
        model.addAttribute("startTime", forum.getStartTime());
        model.addAttribute("endTime", forum.getEndTime());
        return "admin/forum/forumList";
    }
}
