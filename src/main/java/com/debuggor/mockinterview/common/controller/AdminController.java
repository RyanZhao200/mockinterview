package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.service.AdminService;
import com.debuggor.mockinterview.common.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/system/admin/toUpdate")
    public String toAdminInfo(Model model, HttpSession session) {
        String username = (String) session.getAttribute("admin");
        Admin admin = adminService.getAdminByUserName(username);
        model.addAttribute("user", admin);
        return "admin/system/updateAdmin";
    }

    @RequestMapping("/system/admin/updateAdmin")
    public String updateAdmin(Admin admin, HttpSession session) {
        String username = (String) session.getAttribute("username");
        admin.setUsername(username);
        adminService.updateAdmin(admin);
        return "admin/main";
    }

    @RequestMapping("system/admin/resetPwd")
    public String resetPwd() {
        return "admin/system/resetPwd";
    }

    @RequestMapping("/system/admin/updatePwd")
    public String updatePwd(@RequestParam("oldPassword") String oldPassword,
                            @RequestParam("password") String password,
                            @RequestParam("confirm") String confirm,
                            HttpSession session, Model model) {
        // 前端已经验证，密码不可能为空
        if (!password.equals(confirm)) {
            model.addAttribute("info", MockConstant.PASSWORD_NOT_EQU);
            return "admin/system/resetPwd";
        }
        String username = (String) session.getAttribute("admin");
        Admin admin = adminService.getAdminByUserName(username);
        String oldHash = Md5Util.hash(oldPassword);
        if (!oldHash.equals(admin.getPassword())) {
            model.addAttribute("info", MockConstant.PASSWORD_ERROR);
            return "admin/system/resetPwd";
        }
        if (oldPassword.equals(password)) {
            model.addAttribute("info", MockConstant.PASSWORD_EQU);
            return "admin/system/resetPwd";
        }
        admin.setPassword(Md5Util.hash(password));
        adminService.updateAdmin(admin);
        return "admin/main";
    }
}
