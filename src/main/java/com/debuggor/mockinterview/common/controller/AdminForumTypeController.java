package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.forum.bean.Type;
import com.debuggor.mockinterview.forum.service.ForumTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/forum")
public class AdminForumTypeController {
    Logger logger = LoggerFactory.getLogger(AdminForumTypeController.class);

    @Autowired
    private ForumTypeService forumTypeService;

    /**
     * 类别列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/typeList")
    public String typeList(Model model) {
        List<Type> forumTypeList = forumTypeService.getForumTypeList();
        logger.info(forumTypeList.toString());
        model.addAttribute("list", forumTypeList);
        return "admin/forum/typeList";
    }

    /**
     * 添加类别
     *
     * @param type
     * @return
     */
    @RequestMapping("/addType")
    public String addType(Type type) {
        if (type != null) {
            logger.info("插入" + type.getTypeName() + " " + type.getOrderNo());
            forumTypeService.insert(type);
        }
        return "redirect:/admin/forum/typeList";
    }

    /**
     * 删除类别
     *
     * @param tid
     * @return
     */
    @RequestMapping("/delete/{tid}")
    public String delete(@PathVariable("tid") Integer tid) {
        if (tid != null) {
            forumTypeService.delete(tid);
        }
        logger.info("删除帖子类别！");
        return "redirect:/admin/forum/typeList";
    }

    /**
     * 转到编辑类别页面
     *
     * @param tid
     * @param model
     * @return
     */
    @RequestMapping("/toEdit/{tid}")
    public String editPage(@PathVariable("tid") Integer tid, Model model) {
        Type type = forumTypeService.getTypeById(tid);
        model.addAttribute("type", type);
        return "admin/forum/editType";
    }

    /**
     * 提交类别修改
     *
     * @param type
     * @param response
     * @throws IOException
     */
    @RequestMapping("/editType")
    public void editType(Type type, HttpServletResponse response) throws IOException {
        logger.info(type.getTid() + " " + type.getTypeName() + " " + type.getOrderNo());
        if (type != null) {
            forumTypeService.updateType(type);
        }
        StringBuilder str = new StringBuilder();
        str.append("<script>");
        str.append(" window.opener.location.href = window.opener.location.href; window.close();");
        str.append("</script>");
        response.getWriter().write(str.toString());
    }
}
