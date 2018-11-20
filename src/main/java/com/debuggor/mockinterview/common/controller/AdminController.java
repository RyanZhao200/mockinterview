package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.service.AdminService;
import com.debuggor.mockinterview.common.util.FileUploadUtils;
import com.debuggor.mockinterview.common.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequestMapping("/admin")
@Controller
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("/system/admin/toUpdate")
    public String toAdminInfo(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        Admin admin = adminService.getAdminByUserName(username);
        model.addAttribute("admin", admin);
        logger.info(admin.getUsername() + " " + admin.getEmail());
        return "admin/system/updateAdmin";
    }

    @RequestMapping("/system/admin/updateAdmin")
    public String updateAdmin(@RequestParam("headFile") MultipartFile headFile, Admin admin, HttpSession session) throws IOException {
        String username = (String) session.getAttribute("username");
        admin.setUsername(username);
        logger.info(admin.getUsername() + " " + admin.getEmail());
        String headUrl = FileUploadUtils.upload(headFile);
        admin.setHeadUrl(headUrl);
        logger.info(headUrl);
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
        String username = (String) session.getAttribute("username");
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
