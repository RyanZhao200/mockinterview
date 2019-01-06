package com.debuggor.mockinterview.forum.controller;

import com.debuggor.mockinterview.common.enumerate.StatusEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.forum.bean.Comment;
import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.bean.Type;
import com.debuggor.mockinterview.forum.service.CommentService;
import com.debuggor.mockinterview.forum.service.ForumService;
import com.debuggor.mockinterview.forum.service.ForumTypeService;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/forum")
public class ForumController {
    Logger logger = LoggerFactory.getLogger(ForumController.class);

    @Autowired
    private ForumService forumService;
    @Autowired
    private ForumTypeService forumTypeService;
    @Autowired
    private CommentService commentService;

    /**
     * 帖子首页
     *
     * @param pn    第几页
     * @param tid   类别ID
     * @param model
     * @return
     */
    @RequestMapping("")
    public String forumList(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                            @RequestParam(required = false, value = "tid") Integer tid,
                            @RequestParam(required = false, value = "order") Integer order,
                            Model model) {
        Forum forum = new Forum();
        if (tid != null) {
            forum.setTid(tid);
        }
        forum.setOrder(order);
        forum.setForumStatus(StatusEnum.NORMAL.key);
        PageInfo pageInfo = forumService.getPostList(forum, pn);
        List<Type> types = forumTypeService.getForumTypeList();
        List<Forum> hotPosts = forumService.getRecentHotPosts();
        model.addAttribute("hotPosts", hotPosts);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", types);
        // 为前台选中标签起辅助作用
        model.addAttribute("tid", tid);
        model.addAttribute("order", order);
        return "front/forum/forum";
    }

    /**
     * 帖子详细页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("/{pid}")
    public String detailPost(@PathVariable("pid") Integer pid, Model model) {
        Forum forum = forumService.getForumById(pid);
        // 更新浏览次数
        Forum f = new Forum();
        f.setPid(forum.getPid());
        f.setScanCount(forum.getScanCount() + 1);
        forumService.update(f);

        forum = forumService.getForumById(pid);
        List<Comment> comments = commentService.getCommentListByPid(pid);
        List<Forum> hotPosts = forumService.getRecentHotPosts();
        // 上一贴
        Forum preForum = forumService.getForumById(pid - 1);
        // 下一贴
        Forum nextForum = forumService.getForumById(pid + 1);
        model.addAttribute("preForum", shortForumTitle(preForum));
        model.addAttribute("nextForum", shortForumTitle(nextForum));
        model.addAttribute("hotPosts", hotPosts);
        model.addAttribute("forum", forum);
        model.addAttribute("comments", comments);
        return "front/forum/detail";
    }

    /**
     * 变短论坛的标题
     *
     * @param forum
     * @return
     */
    private Forum shortForumTitle(Forum forum) {
        if (forum == null || forum.getTitle() == null) {
            return forum;
        }
        if (forum.getTitle().length() > 15) {
            forum.setTitle(forum.getTitle().substring(0, 15) + "...");
        }
        return forum;
    }

    /**
     * 添加帖子页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String toAddForum(Model model) {
        List<Type> types = forumTypeService.getForumTypeList();
        model.addAttribute("types", types);
        return "front/forum/add";
    }

    /**
     * 添加帖子，成功后跳转到帖子详情页
     *
     * @param forum
     * @param session
     * @return
     */
    @RequestMapping("/action")
    public String addForum(Forum forum, HttpSession session) {

        Finder finder = (Finder) session.getAttribute("finder");
        if (finder != null) {
            forum.setUserType(UserEnum.FINDER.key);
            forum.setUid(finder.getFid());
        }
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        if (interviewer != null) {
            forum.setUserType(UserEnum.INTERVIEWER.key);
            forum.setUid(interviewer.getIid());
        }
        forum.setForumStatus(StatusEnum.NORMAL.key);
        forumService.insertForum(forum);
        // 获得帖子ID
        Integer pid = forum.getPid();
        logger.info("插入数据pid=" + pid);
        return "redirect:/forum/" + pid;
    }

    /**
     * 跳转到编辑帖子页面
     *
     * @param pid
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/updatePost/{pid}")
    public String updatePage(@PathVariable("pid") Integer pid, HttpSession session, Model model) {
        List<Type> types = forumTypeService.getForumTypeList();
        model.addAttribute("types", types);

        Forum forum = forumService.getForumById(pid);
        if (forum == null) {
            return "redirect:/forum";
        }
        // 判断是否是他自己的帖子 如果不是他的帖子，跳转到帖子列表
        Finder finder = (Finder) session.getAttribute("finder");
        if (finder != null) {
            if (forum.getUid().equals(finder.getFid()) && forum.getUserType().equals(UserEnum.FINDER.key)) {
                model.addAttribute("forum", forum);
                return "front/forum/update";
            }
        }
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        if (interviewer != null) {
            if (forum.getUid().equals(interviewer.getIid()) && forum.getUserType().equals(UserEnum.INTERVIEWER.key)) {
                model.addAttribute("forum", forum);
                return "front/forum/update";
            }
        }
        return "redirect:/forum";
    }

    /**
     * 更新帖子
     *
     * @param forum
     * @return
     */
    @RequestMapping("/update")
    public String update(Forum forum) {
        forumService.update(forum);
        return "redirect:/forum/" + forum.getPid();
    }
}
