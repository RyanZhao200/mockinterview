package com.debuggor.mockinterview.common.controller;

import com.debuggor.mockinterview.common.enumerate.CertificationEnum;
import com.debuggor.mockinterview.common.util.TimeUtil;
import com.debuggor.mockinterview.interview.bean.Certification;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.service.CertificationService;
import com.debuggor.mockinterview.interview.service.InterviewerService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 面试官认证controller
 */
@Controller
@RequestMapping("/admin/certification")
public class AdminCertificationController {

    private Logger logger = LoggerFactory.getLogger(AdminCertificationController.class);

    @Autowired
    private CertificationService certificationService;
    @Autowired
    private InterviewerService interviewerService;

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

    /**
     * 面试官A的所有认证信息
     *
     * @param iid
     * @param model
     * @return
     */
    @RequestMapping("/certifications/{iid}")
    public String certificationsByIidPage(@PathVariable("iid") Integer iid, Model model) {
        if (iid == null) {
            return "redirect:/admin/certification/waitCertificationList";
        }
        Interviewer interviewer = interviewerService.getInterviewerById(iid);
        List<Certification> certifications = certificationService.getCertificationsByiid(iid);
        model.addAttribute("interviewer", interviewer);
        model.addAttribute("certifications", certifications);
        return "admin/user/certificationDetail";
    }

    /**
     * 管理员处理面试官的认证信息，认证成功、认证失败
     * 再刷新弹出框，不关闭
     *
     * @return
     */
    @RequestMapping("/dealCertification")
    public void dealCertification(Certification certification, HttpServletResponse response) throws IOException {
        certificationService.update(certification);
        Interviewer interviewer = interviewerService.getInterviewerById(certification.getIid());
        if (interviewer != null) {
            String result = "认证失败！";
            if ("1".equals(certification.getCertificationStatus())) {
                result = "认证成功！";
            }
            logger.info(interviewer.getUsername() + "于" + TimeUtil.format(new Date()) + "认证，结果：" + result);
        }
        StringBuilder str = new StringBuilder();
        str.append("<script>");
        str.append(" window.opener.location.href = window.opener.location.href; window.close();");
        str.append("</script>");
        response.getWriter().write(str.toString());
    }
}
