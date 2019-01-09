package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.util.TimeUtil;
import com.debuggor.mockinterview.interview.bean.Type;
import com.debuggor.mockinterview.interview.service.InterviewTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * IT类别controller
 */
@Controller
@RequestMapping("/admin/interview")
public class AdminInterviewTypeController {
    Logger logger = LoggerFactory.getLogger(AdminInterviewTypeController.class);
    /**
     * 公共的方法
     */
    private UtilController utilController = new UtilController();

    @Autowired
    private InterviewTypeService interviewTypeService;

    /**
     * IT栏目列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/typeList")
    public String typeList(Model model) {
        List<Type> typeList = interviewTypeService.getTypeList();
        List<Type> parentType = interviewTypeService.getTypeByParentId(0);
        model.addAttribute("types", parentType);
        model.addAttribute("list", typeList);
        return "admin/interview/typeList";
    }

    /**
     * 添加一个IT栏目
     *
     * @param type
     * @param request
     * @return
     */
    @RequestMapping("/addType")
    public String addType(Type type, HttpServletRequest request) {
        Admin admin = utilController.getAdminFromSession(request);
        if (type != null) {
            logger.info(admin.getUsername() + "于" + TimeUtil.format(new Date()) + "插入" + type.toString());
            interviewTypeService.insert(type);
        }
        return "redirect:/admin/interview/typeList";
    }

    /**
     * 删除IT栏目
     */
    @RequestMapping("/delete/{tid}")
    public String delete(@PathVariable("tid") Integer tid, HttpServletRequest request) {
        Admin admin = utilController.getAdminFromSession(request);
        if (tid != null) {
            Type type = interviewTypeService.getTypeById(tid);
            logger.info(admin.getUsername() + "于" + TimeUtil.format(new Date()) + "删除" + type.toString());
            interviewTypeService.delete(tid);
        }
        return "redirect:/admin/interview/typeList";
    }

    /**
     * 跳转到编辑IT栏目页面
     *
     * @param tid
     * @param model
     * @return
     */
    @RequestMapping("/toEdit/{tid}")
    public String editPage(@PathVariable("tid") Integer tid, Model model) {
        Type type = interviewTypeService.getTypeById(tid);
        List<Type> parentType = interviewTypeService.getTypeByParentId(0);
        model.addAttribute("parentType", parentType);
        model.addAttribute("type", type);
        return "admin/interview/editType";
    }

    /**
     * 更新IT栏目
     *
     * @param type
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/editType")
    public void editType(Type type, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Admin admin = utilController.getAdminFromSession(request);
        if (type != null) {
            interviewTypeService.updateType(type);
        }
        logger.info(admin.getUsername() + "于" + TimeUtil.format(new Date()) + "修改" + type.toString());
        StringBuilder str = new StringBuilder();
        str.append("<script>");
        str.append(" window.opener.location.href = window.opener.location.href; window.close();");
        str.append("</script>");
        response.getWriter().write(str.toString());
    }
}
