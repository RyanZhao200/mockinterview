package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.interview.bean.Certification;
import com.debuggor.mockinterview.interview.dao.CertificationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CertificationService {
    @Autowired
    private CertificationDao certificationDao;

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
}
