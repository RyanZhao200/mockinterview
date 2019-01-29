package com.debuggor.mockinterview.finance.controller;

import com.debuggor.mockinterview.finance.bean.ExtractRecording;
import com.debuggor.mockinterview.finance.bean.RechargeRecording;
import com.debuggor.mockinterview.finance.service.ExtractRecordingService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/extractRecording")
public class ExtractRecordingController {

    @Autowired
    private ExtractRecordingService extractRecordingService;

    /**
     * 提现记录列表
     *
     * @param pn    页码
     * @param model
     * @return
     */
    @RequestMapping("/extractRecordings")
    public String getExtractRecordings(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                       Model model) {
        PageInfo<ExtractRecording> pageInfo = extractRecordingService.getExtractRecordings(pn);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/finance/extractRecording";
    }
}
