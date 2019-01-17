package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.enumerate.CertificationEnum;
import com.debuggor.mockinterview.interview.bean.Certification;
import com.debuggor.mockinterview.interview.service.CertificationService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 面试官认证controller
 */
@Controller
@RequestMapping("/admin/certification")
public class AdminCertificationController {

    private Logger logger = LoggerFactory.getLogger(AdminCertificationController.class);

    @Autowired
    private CertificationService certificationService;

    /**
     * 待认证的面试官信息列表
     *
     * @param pn
     * @param model
     * @return
     */
    @RequestMapping("/waitCertificationList")
    public String waitCertificationsPage(@RequestParam(required = false, defaultValue = "1", value = "pn") Integer pn,
                                         Model model) {
        Certification certification = new Certification();
        certification.setCertificationStatus(CertificationEnum.WAIT_REVIEW.key);
        PageInfo pageInfo = certificationService.getCertificationList(certification, pn);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/user/waitCertificationList";
    }
}
