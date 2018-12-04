package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminLoginController {
    Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/main")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {
        logger.info(username + "....." + password + ".......");
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        String result = adminService.login(admin);
        if (!MockConstant.LOGIN_SUCCESS.equals(result)) {
            model.addAttribute("msg", result);
            return "admin/login";
        }
        session.setAttribute("username", username);
        return "admin/main";
    }

    @RequestMapping("/index")
    public String index() {
        return "admin/index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("退出登录。。。。。。");
        session.setAttribute("admin", null);
        return "admin/login";
    }

    @GetMapping("/main")
    public String main() {
        return "admin/main";
    }

}
