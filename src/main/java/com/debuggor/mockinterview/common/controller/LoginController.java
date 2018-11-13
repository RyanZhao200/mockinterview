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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    @PostMapping("/index")
    public String index(@RequestParam("username") String username,
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
        session.setAttribute("admin", username);
        model.addAttribute("admin", username);
        return "admin/main";
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        Object username = session.getAttribute("admin");
        model.addAttribute("admin", username);
        return "admin/main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("退出登录。。。。。。");
        session.setAttribute("admin", null);
        return "admin/login";
    }

    @RequestMapping("/main")
    public String main(HttpSession session, Model model) {
        String username = (String) session.getAttribute("admin");
        Admin admin = adminService.getAdminByUserName(username);
        model.addAttribute("admin", admin.getUsername());
        return "admin/main";
    }


}
