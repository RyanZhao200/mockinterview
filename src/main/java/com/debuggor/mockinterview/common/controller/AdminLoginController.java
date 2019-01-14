package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.service.AdminService;
import com.debuggor.mockinterview.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RequestMapping("/admin")
@Controller
public class AdminLoginController {
    private Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

    @Autowired
    private AdminService adminService;

    /**
     * 转到后台登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "admin/login";
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/loginAction")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, RedirectAttributes redirectAttributes) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        String result = adminService.login(admin);
        if (!MockConstant.LOGIN_SUCCESS.equals(result)) {
            redirectAttributes.addFlashAttribute("msg", result);
            return "redirect:/admin/login";
        }
        admin = adminService.getAdminByUserName(username);
        session.setAttribute("admin", admin);
        logger.info(username + "于" + TimeUtil.format(new Date()) + "登录后台");
        return "redirect:/admin";
    }

    /**
     * 后台首页
     *
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("")
    public String index(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "admin/index";
    }

    /**
     * 后台管理员退出登录，销毁session
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        logger.info(admin.getUsername() + "于" + TimeUtil.format(new Date()) + "登出后台");
        session.invalidate();
        return "admin/login";
    }

    /**
     * 后台首页
     *
     * @return
     */
    @RequestMapping("/home")
    public String home() {
        return "admin/home";
    }

}
