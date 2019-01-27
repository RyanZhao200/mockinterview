package com.debuggor.mockinterview.finance.controller;

import com.debuggor.mockinterview.finance.bean.RechargeRecording;
import com.debuggor.mockinterview.finance.service.RechargeRecordingService;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 求职者的充值记录
 */
@Controller
@RequestMapping("/recharge")
public class RechargeRecordingController {

    @Autowired
    private RechargeRecordingService recordingService;

    /**
     * 求职者的充值记录历史页面
     *
     * @param pn
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/recharges")
    public String rechargesForFinder(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                     HttpSession session, Model model) {
        Finder finder = (Finder) session.getAttribute("finder");
        model.addAttribute("finder", finder);
        PageInfo<RechargeRecording> pageInfo = recordingService.getRechargeRecordingsByUid(finder.getFid(), pn);
        model.addAttribute("pageInfo", pageInfo);
        return "front/user/finder/recharges";
    }

    /**
     * 后台：充值记录列表
     *
     * @param pn
     * @param model
     * @return
     */
    @RequestMapping("/rechargeRecordings")
    public String getRechargeRecordings(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn, Model model) {
        PageInfo<RechargeRecording> pageInfo = recordingService.getRechargeRecordings(pn);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/finance/rechargeRecording";
    }
}
