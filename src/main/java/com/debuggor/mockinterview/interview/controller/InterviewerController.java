package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.bean.Message;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.constant.QiniuConstant;
import com.debuggor.mockinterview.common.enumerate.MessageStatusEnum;
import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.common.service.MessageService;
import com.debuggor.mockinterview.common.service.QiniuService;
import com.debuggor.mockinterview.common.util.Md5Util;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.InterviewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 面试官controller
 */
@Controller
@RequestMapping("/interviewer")
public class InterviewerController {

    private Logger logger = LoggerFactory.getLogger(InterviewerController.class);

    @Autowired
    private InterviewerService interviewerService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private MessageService messageService;

    @RequestMapping("/login")
    public String tologin() {
        return "front/user/interviewer/login";
    }

    /**
     * 面试官登录
     *
     * @return
     */
    @RequestMapping("/loginAction")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model, HttpSession session,
                        HttpServletRequest request) {
        String result = interviewerService.login(email, password);
        if (!MockConstant.LOGIN_SUCCESS.equals(result)) {
            model.addAttribute("msg", result);
            return "front/user/interviewer/login";
        }
        Interviewer interviewer = interviewerService.getInterviewerByEmail(email);
        String requestLocalAddr = request.getLocalAddr();
        logger.info(interviewer.getUsername() + "于" + new Date() + "在" + requestLocalAddr + "登录！");
        session.setAttribute("interviewer", interviewer);
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/interviewer/login";
    }

    @RequestMapping("/register")
    public String toRegister() {
        return "front/user/interviewer/register";
    }

    /**
     * 面试官注册
     *
     * @param interviewer
     * @param model
     * @return
     */
    @RequestMapping("/registerAction")
    public String register(Interviewer interviewer, String repassword, Model model) {
        logger.info(interviewer.getEmail() + " " + interviewer.getUsername());
        String result = interviewerService.register(interviewer, repassword);
        if (!"ok".equals(result)) {
            model.addAttribute("msg", result);
            return "front/user/interviewer/register";
        }
        return "front/user/interviewer/tips";
    }

    @RequestMapping("/activate")
    public String activate(String code) {
        Integer activate = interviewerService.isActivate(code);
        if (activate == null) {
            return "redirect:/interviewer/register";
        }
        return "redirect:/interviewer/login";
    }

    @RequestMapping("/toUpdate")
    public String toSetFinderPage(HttpSession session, Model model) {
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        model.addAttribute("interviewer", interviewer);
        return "front/user/interviewer/set";
    }

    /**
     * 跟新面试官的信息
     *
     * @return
     */
    @RequestMapping("/update")
    public String update(Interviewer interviewer, HttpSession session) {
        logger.info("面试官跟新：" + interviewer.toString());
        interviewerService.update(interviewer);
        Interviewer user = (Interviewer) session.getAttribute("interviewer");
        Interviewer interviewerByEmail = interviewerService.getInterviewerByEmail(user.getEmail());
        session.setAttribute("interviewer", interviewerByEmail);
        return "redirect:/interviewer/toUpdate";
    }

    @RequestMapping(value = "/updateHeadUrl", method = {RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
    public String updateHeadUrl(MultipartFile headUrl, Integer iid, HttpSession session, Model model) throws IOException {
        // 文件类型限制
        String[] allowedType = {"image/bmp", "image/gif", "image/jpeg", "image/png", "image/jpg"};
        boolean allowed = Arrays.asList(allowedType).contains(headUrl.getContentType());
        if (!allowed) {
            model.addAttribute("msg", "error|不支持的类型");
            return "front/user/interviewer/set";
        }
        // 图片大小限制
        if (headUrl.getSize() > 3 * 1024 * 1024) {
            model.addAttribute("msg", "error|图片大小不能超过3M");
            return "front/user/interviewer/set";
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

        Interviewer interviewer = new Interviewer();
        interviewer.setIid(iid);
        interviewer.setHeadUrl(headImage);
        interviewerService.update(interviewer);

        Interviewer user = (Interviewer) session.getAttribute("interviewer");
        interviewer = interviewerService.getInterviewerByEmail(user.getEmail());
        session.setAttribute("interviewer", interviewer);
        model.addAttribute("interviewer", interviewer);
        return "redirect:/interviewer/toUpdate";
    }

    /**
     * 面试官修改密码的时候判断当前密码是否正确
     *
     * @param oldPwd
     * @return
     */
    @RequestMapping("/judgeOldPwd")
    public @ResponseBody
    String judgeOldPwd(@RequestParam(required = false, value = "oldPwd") String oldPwd,
                       HttpSession session) {
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        String oldHash = Md5Util.hash(oldPwd);
        if (!interviewer.getPassword().equals(oldHash)) {
            return "no";
        }
        return "success";
    }

    /**
     * 面试官修改密码
     *
     * @param session
     * @return
     */
    @RequestMapping("/updatePassword")
    public @ResponseBody
    String updatePassword(@RequestParam(required = false, value = "pwd") String pwd,
                          HttpSession session) {
        Interviewer interviewer1 = (Interviewer) session.getAttribute("interviewer");
        Interviewer interviewer = new Interviewer();
        interviewer.setIid(interviewer1.getIid());
        String hash = Md5Util.hash(pwd);
        interviewer.setPassword(hash);
        interviewerService.update(interviewer);
        return "success";
    }

    /**
     * 面试官 面试的记录
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/messageInterview")
    public String toMessageInterview(HttpSession session, Model model) {
        Interviewer interviewer = (Interviewer) session.getAttribute("interviewer");
        model.addAttribute("interviewer", interviewer);

        List<Message> messages = messageService.getMessageByUid(interviewer.getIid(), UserEnum.INTERVIEWER.key);
        model.addAttribute("messages", messages);
        return "front/user/interviewer/messageInterview";
    }

    /**
     * 面试官删除面试记录（改变消息状态，让面试官不可见）
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
        return "redirect:/interviewer/messageInterview";
    }

    /**
     * 根据面试官ID，删除面试官的面试记录
     *
     * @param iid
     * @return
     */
    @RequestMapping("/deleteMessageAll/{iid}")
    public String deleteMessageAll(@PathVariable("iid") Integer iid) {
        messageService.updateByUid(iid, UserEnum.INTERVIEWER.key);
        return "redirect:/interviewer/messageInterview";
    }
}
