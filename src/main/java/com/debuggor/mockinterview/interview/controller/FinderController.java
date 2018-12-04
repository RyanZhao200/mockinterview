package com.debuggor.mockinterview.interview.controller;

import com.debuggor.mockinterview.interview.bean.Finder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/***
 * 求职者管理Controller
 */
@Controller
@RequestMapping("/finder")
public class FinderController {
    Logger logger = LoggerFactory.getLogger(FinderController.class);

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

    @RequestMapping("/loginAction")
    public String loginToIndex(Finder finder, HttpSession session) {

        return "front/forum/forum";
    }
}
