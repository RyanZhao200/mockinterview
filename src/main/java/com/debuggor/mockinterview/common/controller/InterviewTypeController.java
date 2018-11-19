package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.service.InterviewTypeService;
import com.debuggor.mockinterview.interview.bean.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * IT类别controller
 */
@Controller
@RequestMapping("/admin/interview")
public class InterviewTypeController {

    @Autowired
    private InterviewTypeService interviewTypeService;

    @RequestMapping("/typeList")
    public String typeList(Model model) {
        List<Type> typeList = interviewTypeService.getTypeList();
        model.addAttribute("list", typeList);
        return "admin/interView/typeList";
    }
}
