package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.service.AdminService;
import com.debuggor.mockinterview.common.util.Md5Util;
import com.debuggor.mockinterview.common.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/admin")
public class AdminAdminController {
    private Logger logger = LoggerFactory.getLogger(AdminAdminController.class);

    private UtilController utilController = new UtilController();

    @Autowired
    private AdminService adminService;

    /**
     * 转到管理员修改资料页面
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/admin/toUpdate")
    public String toAdminInfo(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "admin/system/updateAdmin";
    }

    /**
     * 提交修改的信息
     *
     * @param admin
     * @return
     */
    @RequestMapping("/admin/updateAdmin")
    public String updateAdmin(Admin admin, HttpServletRequest request,
                              RedirectAttributes redirectAttributes) {
        Admin admin1 = utilController.getAdminFromSession(request);
        if (admin1 == null || admin == null || !admin.getUsername().equals(admin1.getUsername())) {
            redirectAttributes.addFlashAttribute("info", "发生错误！");
            return "redirect:/admin/admin/toUpdate";
        }
        adminService.updateAdmin(admin);
        redirectAttributes.addFlashAttribute("info", "修改成功！");
        logger.info(admin.getUsername() + "于" + TimeUtil.format(new Date()) + "修改信息!" + admin.toString());
        return "redirect:/admin/admin/toUpdate";
    }

    /**
     * 转到修改密码页面
     *
     * @return
     */
    @RequestMapping("/admin/resetPwd")
    public String resetPwd() {
        return "admin/system/resetPwd";
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param password
     * @param confirm
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/admin/updatePwd")
    public String updatePwd(@RequestParam("oldPassword") String oldPassword,
                            @RequestParam("password") String password,
                            @RequestParam("confirm") String confirm,
                            HttpServletRequest request, Model model,
                            RedirectAttributes redirectAttributes) {
        // 前端已经验证，密码不可能为空
        if (!password.equals(confirm)) {
            model.addAttribute("info", MockConstant.PASSWORD_NOT_EQU);
            return "admin/system/resetPwd";
        }
        Admin admin = utilController.getAdminFromSession(request);
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
        logger.info(admin.getUsername() + "于" + TimeUtil.format(new Date()) + "修改密码!" + admin.toString());
        redirectAttributes.addFlashAttribute("info", MockConstant.PASSWORD_UPDATE_SUCCESS);
        return "redirect:/admin/admin/resetPwd";
    }
}
