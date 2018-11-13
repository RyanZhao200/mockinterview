package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/system/admin/profile")
    public String adminInfo(Model model, HttpSession session) {
        String username = (String) session.getAttribute("admin");
        Admin admin = adminService.getAdminByUserName(username);
        model.addAttribute("user", admin);
        return "admin/system/admin/profile";
    }

    @RequestMapping("system/admin/resetPwd")
    public String resetPwd(Model model, HttpSession session) {
        String username = (String) session.getAttribute("admin");
        Admin admin = adminService.getAdminByUserName(username);
        model.addAttribute("user", admin);
        return "admin/system/resetPwd";
    }

}
