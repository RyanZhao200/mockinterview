package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.util.Md5Util;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 面试官service层
 */
@Service
public class InterviewerService {

    @Autowired
    InterviewerDao interviewerDao;

    /**
     * 面试官登录
     *
     * @param email
     * @param password
     * @return
     */
    public String login(String email, String password) {
        if (email == null || password == null) {
            return MockConstant.LOGIN_ERROR;
        }
        Interviewer interviewer = interviewerDao.getInterviewerByEmail(email);
        if (interviewer == null) {
            return MockConstant.LOGIN_ERROR;
        }
        String passwordMD5 = Md5Util.hash(password);
        if (!passwordMD5.equals(interviewer.getPassword())) {
            return MockConstant.LOGIN_ERROR;
        }
        return MockConstant.LOGIN_SUCCESS;
    }

    public Interviewer getInterviewerByEmail(String email) {
        Interviewer interviewer = null;
        if (email != null) {
            interviewer = interviewerDao.getInterviewerByEmail(email);
        }
        return interviewer;
    }

}
