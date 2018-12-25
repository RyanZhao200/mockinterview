package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.bean.Message;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.constant.QiniuConstant;
import com.debuggor.mockinterview.common.enumerate.MessageStatusEnum;
import com.debuggor.mockinterview.common.enumerate.StatusEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.common.service.MessageService;
import com.debuggor.mockinterview.common.service.QiniuService;
import com.debuggor.mockinterview.common.util.Md5Util;
import com.debuggor.mockinterview.forum.bean.Comment;
import com.debuggor.mockinterview.forum.bean.Forum;
import com.debuggor.mockinterview.forum.service.CommentService;
import com.debuggor.mockinterview.forum.service.ForumService;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Order;
import com.debuggor.mockinterview.interview.service.FinderService;
import com.debuggor.mockinterview.interview.service.InterviewerService;
import com.debuggor.mockinterview.interview.service.OrdersService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/***
 * 求职者管理Controller
 */
@Controller
@RequestMapping("/finder")
public class FinderController {

    private Logger logger = LoggerFactory.getLogger(FinderController.class);

    @Autowired
    private FinderService finderService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private ForumService forumService;
    @Autowired
    private InterviewerService interviewerService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CommentService commentService;

    /**
     * 求职者登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "front/user/finder/login";
    }

    /**
     * 求职者注册页面
     *
     * @return
     */
    @RequestMapping("/reg")
    public String reg() {
        return "front/user/finder/reg";
    }

    /**
     * 用户登录成功后，挑战到首页
     *
     * @param email
     * @param password
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/loginAction")
    public String loginToIndex(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model, HttpSession session) {
        Finder finder = new Finder();
        finder.setEmail(email);
        finder.setPassword(password);

        String result = finderService.login(finder);
        if (!result.equals(MockConstant.LOGIN_SUCCESS)) {
            model.addAttribute("msg", result);
            // 这里存在问题，msg不能带过去
            return "redirect:/finder/login";
        }
        Finder user = finderService.getFinderByEmail(email);
        logger.info(user.getUsername() + "于" + new Date() + "登录");
        session.setAttribute("finder", user);
        return "redirect:/forum";
    }

    /**
     * 注册
     *
     * @param finder
     * @param repassword
     * @param model
     * @return
     */
    @RequestMapping("/register")
    public String register(Finder finder, String repassword, Model model) {
        String result = finderService.register(finder, repassword);
        if (!"ok".equals(result)) {
            model.addAttribute("msg", result);
            return "front/user/finder/reg";
        }
        return "front/user/finder/tips";
    }

    /***
     * 登出
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/finder/login";
    }

    /***
     * 求职者设置页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toSetFinderPage(HttpSession session, Model model) {
        Finder finder = (Finder) session.getAttribute("finder");
        model.addAttribute("finder", finder);
        return "front/user/finder/set";
    }

    @RequestMapping("/update")
    public String update(Finder finder, HttpSession session) {
        logger.info("用户跟新操作：" + finder.toString());
        finderService.update(finder);
        Finder user = (Finder) session.getAttribute("finder");
        Finder finderByEmail = finderService.getFinderByEmail(user.getEmail());
        session.setAttribute("finder", finderByEmail);

        return "redirect:/finder/toUpdate";
    }

    @RequestMapping(value = "/updateHeadUrl", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public String updateHeadUrl(MultipartFile headUrl, Integer fid, HttpSession session, Model model) throws IOException {
        // 文件类型限制
        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png", "image/jpg"};
        boolean allowed = Arrays.asList(allowedType).contains(headUrl.getContentType());
        if (!allowed) {
            model.addAttribute("msg", "error|不支持的类型");
            return "front/user/finder/set";
        }
        // 图片大小限制
        if (headUrl.getSize() > 3 * 1024 * 1024) {
            model.addAttribute("msg", "error|图片大小不能超过3M");
            return "front/user/finder/set";
        }
        // 包含原始文件名的字符串
        String fi = headUrl.getOriginalFilename();
        // 提取文件拓展名
        String fileNameExtension = fi.substring(fi.indexOf("."), fi.length());
        // 生成云端的真实文件名
        String remoteFileName = UUID.randomUUID().toString() + fileNameExtension;
        qiniuService.upload(headUrl.getBytes(), remoteFileName);
        // 返回图片的URL地址
        String headImage = QiniuConstant.QINIU_IMAGE_URL + remoteFileName;
        logger.info(headImage);

        Finder finder = new Finder();
        finder.setFid(fid);
        finder.setHeadUrl(headImage);
        finderService.update(finder);

        Finder user = (Finder) session.getAttribute("finder");
        user = finderService.getFinderByEmail(user.getEmail());
        session.setAttribute("finder", user);
        model.addAttribute("finder", user);
        return "front/user/finder/set";
    }

    /**
     * 求职者修改密码的时候判断当前密码是否正确
     *
     * @param oldPwd
     * @return
     */
    @RequestMapping("/judgeOldPwd")
    public @ResponseBody
    String judgeOldPwd(@RequestParam(required = false, value = "oldPwd") String oldPwd,
                       HttpSession session) {
        Finder finder = (Finder) session.getAttribute("finder");
        String oldHash = Md5Util.hash(oldPwd);
        if (!finder.getPassword().equals(oldHash)) {
            return "no";
        }
        return "success";
    }

    /**
     * 求职者修改密码
     *
     * @param session
     * @return
     */
    @RequestMapping("/updatePassword")
    public @ResponseBody
    String updatePassword(@RequestParam(required = false, value = "pwd") String pwd,
                          HttpSession session) {
        Finder finder1 = (Finder) session.getAttribute("finder");
        Finder finder = new Finder();
        finder.setFid(finder1.getFid());
        String hash = Md5Util.hash(pwd);
        finder.setPassword(hash);
        finderService.update(finder);
        return "success";
    }

    @RequestMapping("/forget")
    public String forgetPasswordPage() {
        return "front/user/finder/forget";
    }

    @RequestMapping("/activate")
    public String activate(String code, Model model) {
        Integer affected = finderService.isActivate(code);
        if (affected != null) {
            model.addAttribute("msg", "邮箱已验证，请登录");
            return "redirect:/finder/login";
        } else {
            model.addAttribute("msg", "验证失败，请重新注册");
            return "redirect:/finder/reg";
        }
    }

    /**
     * 求职者 面试消息记录
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/messageInterview")
    public String toMessageInterview(HttpSession session, Model model) {
        Finder finder = (Finder) session.getAttribute("finder");
        model.addAttribute("finder", finder);

        List<Message> messages = messageService.getMessageByUid(finder.getFid(), UserEnum.FINDER.key);
        model.addAttribute("messages", messages);
        return "front/user/finder/messageInterview";
    }

    /**
     * 求职者删除面试记录（改变消息状态，让求职者不可见）
     *
     * @param mid
     * @return
     */
    @RequestMapping("/deleteMessage/{mid}")
    public String deleteMessageByMid(@PathVariable("mid") Integer mid) {
        Message message = new Message();
        message.setMid(mid);
        message.setMessageStatus(MessageStatusEnum.DELETE.key);
        message.setUpdateTime(new Date());
        messageService.update(message);
        return "redirect:/finder/messageInterview";
    }

    /**
     * 根据求职者ID，删除求职者的所有面试记录
     *
     * @param fid
     * @return
     */
    @RequestMapping("/deleteMessageAll/{fid}")
    public String deleteMessageAll(@PathVariable("fid") Integer fid) {
        messageService.updateByUid(fid, UserEnum.FINDER.key);
        return "redirect:/finder/messageInterview";
    }

    /**
     * 求职者的 帖子页面、评论
     */
    @RequestMapping("/posts")
    public String postPage(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                           Model model, HttpSession session) {
        Finder finder = (Finder) session.getAttribute("finder");
        model.addAttribute("finder", finder);

        PageInfo<Comment> commentPageInfo = commentService.getCommentListByUid(pn, finder.getFid(), UserEnum.FINDER.key);
        PageInfo<Forum> forumPageInfo = forumService.getForumsByUid(pn, finder.getFid(), UserEnum.FINDER.key);
        model.addAttribute("commentPageInfo", commentPageInfo);
        model.addAttribute("forumPageInfo", forumPageInfo);
        return "front/user/finder/myPost";
    }

    /**
     * 删除帖子（逻辑上删除，改变帖子状态，让用户不可见）
     *
     * @return
     */
    @RequestMapping("/deletePost/{pid}")
    public String deletePost(@PathVariable("pid") Integer pid) {
        logger.info(String.valueOf(pid));
        Forum forum = new Forum();
        forum.setPid(pid);
        forum.setForumStatus(StatusEnum.DELETE.key);
        forumService.update(forum);
        return "redirect:/finder/posts";
    }

    /**
     * 到求职者的主页（帖子、评论，求职者的基本信息）
     *
     * @return
     */
    @RequestMapping("/home/{fid}")
    public String toFinderHome(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                               @PathVariable("fid") Integer fid, Model model) {
        if (fid == null) {
            return "redirect:/";
        }
        Finder finder = finderService.getFinderById(fid);
        if (finder == null) {
            return "redirect:/";
        }
        PageInfo<Forum> forumPageInfo = forumService.getForumsByUid(pn, fid, UserEnum.FINDER.key);
        PageInfo<Comment> commentPageInfo = commentService.getCommentListByUid(pn, fid, UserEnum.FINDER.key);
        model.addAttribute("finder", finder);
        model.addAttribute("forumPageInfo", forumPageInfo);
        model.addAttribute("commentPageInfo", commentPageInfo);
        return "front/user/finder/home";
    }
}
