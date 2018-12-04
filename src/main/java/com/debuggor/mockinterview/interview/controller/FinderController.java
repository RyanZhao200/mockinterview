package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.service.FinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

/***
 * 求职者管理Controller
 */
@Controller
@RequestMapping("/finder")
public class FinderController {

    Logger logger = LoggerFactory.getLogger(FinderController.class);

    @Autowired
    private FinderService finderService;

    /**
     * 求职者登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "front/user/login";
    }

    /**
     * 求职者注册页面
     *
     * @return
     */
    @RequestMapping("/reg")
    public String reg() {
        return "front/user/reg";
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
        session.setAttribute("user", user);
        return "redirect:/forum";
    }

    /***
     * 登出
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return "redirect:/finder/login";
    }

    /***
     * 求职者设置页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toSetFinderPage() {
        return "front/user/set";
    }

    @RequestMapping("/forget")
    public String forgetPasswordPage() {
        return "front/user/forget";
    }
}
