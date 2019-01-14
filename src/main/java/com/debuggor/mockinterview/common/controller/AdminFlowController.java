package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.interview.bean.Flow;
import com.debuggor.mockinterview.interview.service.FlowService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/flow")
public class AdminFlowController {

    private Logger logger = LoggerFactory.getLogger(AdminFlowController.class);

    @Autowired
    private FlowService flowService;

    /**
     * 后台流水列表页面
     *
     * @param pn
     * @param flow
     * @param model
     * @return
     */
    @RequestMapping("/flowList")
    public String flowsPage(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                            Flow flow, Model model) {
        PageInfo pageInfo = flowService.getFlowList(flow, pn);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/interview/flowList";
    }

}
