package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.service.FinderService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/user")
public class AdminFinderController {
    Logger logger = LoggerFactory.getLogger(AdminFinderController.class);

    @Autowired
    private FinderService finderService;

    @RequestMapping("/finderList")
    public String toFinderListPage(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                   Finder finder, Model model) {
        PageInfo pageInfo = finderService.getFinderList(finder, pn);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/user/finderList";
    }
}
