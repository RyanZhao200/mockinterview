package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.async.MailTask;
import com.debuggor.mockinterview.common.constant.MailConstant;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.util.ActivateCodeUtil;
import com.debuggor.mockinterview.common.util.Md5Util;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 面试官service层
 */
@Service
public class InterviewerService {

    @Autowired
    private InterviewerDao interviewerDao;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TaskExecutor taskExecutor;

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

    public Interviewer getInterviewerById(Integer iid) {
        Interviewer interviewer = null;
        if (iid != null) {
            interviewer = interviewerDao.getInterviewerById(iid);
        }
        return interviewer;
    }

    public String register(Interviewer interviewer, String repassword) {
        if (interviewer == null || repassword == null) {
            return null;
        }
        if (!repassword.equals(interviewer.getPassword())) {
            return "两次密码不一致";
        }
        Interviewer interviewerByEmail = interviewerDao.getInterviewerByEmail(interviewer.getEmail());
        if (interviewerByEmail != null) {
            return "该邮箱已注册，换其他邮箱试试";
        }
        // 对密码进行加密
        interviewer.setPassword(Md5Util.hash(interviewer.getPassword()));
        // 设置默认头像
        interviewer.setHeadUrl("https://dn-qiniu-avatar.qbox.me/avatar/6219d3089bbb149606e87debb24ddbdd?qiniu-avatar");
        // 设置未激活
        interviewer.setIsActivate(0);
        // 设置未认证
        interviewer.setIsCertification(0);
        //注册时间
        interviewer.setCreateTime(new Date());
        // 设置激活码
        String activateCode = ActivateCodeUtil.createActivateCode();
        interviewer.setActivateCode(activateCode);
        //设置默认性别为男
        interviewer.setSex(0);
        interviewerDao.insert(interviewer);
        taskExecutor.execute(new MailTask(activateCode, MailConstant.MAIL_FROM,
                interviewer.getEmail(), javaMailSender, MailConstant.REGISTERED_INTERVIEWER));
        return "ok";
    }

    /**
     * 激活面试官邮箱
     *
     * @param code
     */
    public Integer isActivate(String code) {
        Integer result = null;
        if (code != null) {
            result = interviewerDao.updateActivate(code);
        }
        return result;
    }

    /**
     * 跟新面试官信息
     *
     * @param interviewer
     */
    public void update(Interviewer interviewer) {
        if (interviewer != null) {
            interviewerDao.update(interviewer);
        }
    }

    /**
     * 获取面试官数量
     *
     * @return
     */
    public Integer getInterviewerNum() {
        Integer num = interviewerDao.getInterviewerNum();
        return num;
    }
}
