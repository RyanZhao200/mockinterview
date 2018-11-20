package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.service.InterviewTypeService;
import com.debuggor.mockinterview.interview.bean.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * IT类别controller
 */
@Controller
@RequestMapping("/admin/interview")
public class InterviewTypeController {
    Logger logger = LoggerFactory.getLogger(InterviewTypeController.class);

    @Autowired
    private InterviewTypeService interviewTypeService;

    @RequestMapping("/typeList")
    public String typeList(Model model) {
        List<Type> typeList = interviewTypeService.getTypeList();
        List<Type> parentType = interviewTypeService.getTypeByParentId(0);
        Type type = new Type();
        type.setTid(0);
        type.setTypeName("无父栏目");
        parentType.add(type);
        model.addAttribute("types",parentType);
        model.addAttribute("list", typeList);
        return "admin/interView/typeList";
    }

    @RequestMapping("/addType")
    public String addType(Type type){
        logger.info(type.getTypeName()+" "+type.getParentId()+" "+type.getOrderNo());
        interviewTypeService.insert(type);
        logger.info("添加栏目成功！");
        return "redirect:/admin/interview/typeList";
    }

    @RequestMapping("/delete/{tid}")
    public String delete(@PathVariable("tid") Integer tid){
        if (tid != null){
            interviewTypeService.delete(tid);
        }
        logger.info("删除栏目成功！");
        return "redirect:/admin/interview/typeList";
    }
}
