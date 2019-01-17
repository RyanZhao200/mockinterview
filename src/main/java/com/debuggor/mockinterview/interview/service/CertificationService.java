package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.interview.bean.Certification;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.CertificationDao;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CertificationService {
    @Autowired
    private CertificationDao certificationDao;
    @Autowired
    private InterviewerDao interviewerDao;

    /**
     * 面试官最近的一次认证信息
     */
    public Certification getLastCertificationByiid(Integer iid) {
        Certification certification = certificationDao.getLastCertificationByiid(iid);
        return certification;
    }

    /**
     * 插入一条认证信息
     *
     * @param certification
     */
    public void insert(Certification certification) {
        certification.setCreateTime(new Date());
        certificationDao.insert(certification);
    }

    /**
     * 更新认证信息
     *
     * @param certification
     */
    public void update(Certification certification) {
        certification.setReviewTime(new Date());
        certificationDao.update(certification);
    }

    /**
     * 面试官认证的信息列表
     *
     * @param certification
     * @param pn
     */
    public PageInfo getCertificationList(Certification certification, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Certification> certifications = certificationDao.getCertificationList(certification);
        PageInfo<Certification> pageInfo = new PageInfo<>(certifications, PageConstant.Navigate_Pages);
        // 给每条认证信息加上面试官的信息
        List<Certification> list = new ArrayList<>();
        for (Certification c : certifications) {
            Interviewer interviewer = interviewerDao.getInterviewerById(c.getIid());
            c.setInterviewer(interviewer);
            list.add(c);
        }
        pageInfo.setList(list);
        return pageInfo;
    }
}
