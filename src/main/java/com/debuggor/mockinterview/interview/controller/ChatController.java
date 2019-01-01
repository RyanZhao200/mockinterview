package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.enumerate.UserEnum;
import com.debuggor.mockinterview.interview.bean.Chat;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.ChatService;
import com.debuggor.mockinterview.interview.service.FinderService;
import com.debuggor.mockinterview.interview.service.InterviewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/interview")
public class ChatController {
    private Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private FinderService finderService;
    @Autowired
    private InterviewerService interviewerService;
    @Autowired
    private ChatService chatService;

    /**
     * 登陆界面
     */
    @RequestMapping("/")
    public String login() {
        return "front/interview/interview/login";
    }

    /**
     * 求职者聊天界面
     * code = Math.random().toString(36).substr(2)-(fid*7+1)-Math.random().toString(36).substr(2);
     * 用户没登录的时候也可以打开这个界面
     */
    @RequestMapping("/interviewee")
    public String interviewee(@PathParam("code") String code, Model model, HttpServletRequest request) throws UnknownHostException {
        Integer fid = getFidUtils(code);
        Integer iid = getIidUtils(code);
        Finder finder = finderService.getFinderById(fid);
        Interviewer interviewer = interviewerService.getInterviewerById(iid);
        if (finder == null || interviewer == null) {
            return "redirect:/";
        }
        logger.info(new Date() + "求职者" + finder.getUsername() + "进入与面试官" + interviewer.getUsername() + "面试的页面");
        Chat chat = new Chat(fid, UserEnum.FINDER.key, iid, UserEnum.INTERVIEWER.key);
        List<Chat> chats = chatService.getChatListForDouble(chat);
        model.addAttribute("chats", chats);
        model.addAttribute("finder", finder);
        model.addAttribute("interviewer", interviewer);
        model.addAttribute("webSocketUrl", "ws://" + InetAddress.getLocalHost().getHostAddress() + ":" + request.getServerPort() + request.getContextPath() + "/chat/" + fid + "/" + UserEnum.FINDER.key + "/" + iid);
        return "front/interview/interview/interviewee";
    }

    /**
     * 面试官聊天界面
     *
     * @return
     */
    @RequestMapping("/interviewer")
    public String interviewer(@PathParam("code") String code, Model model, HttpServletRequest request) throws UnknownHostException {
        Integer fid = getFidUtils(code);
        Integer iid = getIidUtils(code);
        Finder finder = finderService.getFinderById(fid);
        Interviewer interviewer = interviewerService.getInterviewerById(iid);
        if (finder == null || interviewer == null) {
            return "redirect:/";
        }
        logger.info(new Date() + "面试官" + interviewer.getUsername() + "进入与求职者" + finder.getUsername() + "面试的页面");
        Chat chat = new Chat(iid, UserEnum.INTERVIEWER.key, fid, UserEnum.FINDER.key);
        List<Chat> chats = chatService.getChatListForDouble(chat);
        model.addAttribute("chats", chats);
        model.addAttribute("finder", finder);
        model.addAttribute("interviewer", interviewer);
        model.addAttribute("webSocketUrl", "ws://" + InetAddress.getLocalHost().getHostAddress() + ":" + request.getServerPort() + request.getContextPath() + "/chat/" + fid + "/" + UserEnum.INTERVIEWER.key + "/" + iid);
        return "front/interview/interview/interviewer";
    }

    @RequestMapping("/insert")
    public @ResponseBody
    String insert(Chat chat) {
        if (chat == null) {
            return "error";
        }
        chatService.insert(chat);
        return "success";
    }

    /**
     * 根据所传code解析出uid
     * Math.random().toString(36).substr(10)+'-'+
     * String(93*7+1)+'-'+String(93*13+2)+'-'+
     * String(93*17+3)+'-'+String(93*19+4)+'-'+
     * Math.random().toString(20).substr(10);
     * <p>
     * "50s-652-1211-1584-1771-bdhf" 93
     *
     * @param code
     * @return
     */
    private Integer getFidUtils(String code) {
        String[] split = code.split("-");
        if (split.length < 3) {
            return -1;
        }
        String s1 = split[1];
        String s2 = split[2];
        if (isNumeric(s1) && isNumeric(s2)) {
            Integer i1 = Integer.parseInt(s1);
            Integer i2 = Integer.parseInt(s2);
            Integer uid1 = (i1 - 1) / 7;
            Integer uid2 = (i2 - 2) / 13;
            if (uid1.equals(uid2)) {
                return uid1;
            } else {
                return -1;
            }
        }
        return -1;
    }

    private Integer getIidUtils(String code) {
        String[] split = code.split("-");
        if (split.length < 5) {
            return -1;
        }
        String s1 = split[3];
        String s2 = split[4];
        if (isNumeric(s1) && isNumeric(s2)) {
            Integer i1 = Integer.parseInt(s1);
            Integer i2 = Integer.parseInt(s2);
            Integer uid1 = (i1 - 3) / 17;
            Integer uid2 = (i2 - 4) / 19;
            if (uid1.equals(uid2)) {
                return uid1;
            } else {
                return -1;
            }
        }
        return -1;
    }

    private boolean isNumeric(String str) {
        String s = "[0-9]*";
        Pattern pattern = Pattern.compile(s);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}
