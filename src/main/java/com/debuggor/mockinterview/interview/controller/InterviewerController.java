package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.InterviewerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 面试官controller
 */
@Controller
@RequestMapping("/interviewer")
public class InterviewerController {

    private Logger logger = LoggerFactory.getLogger(InterviewerController.class);

    @Autowired
    private InterviewerService interviewerService;

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

}
