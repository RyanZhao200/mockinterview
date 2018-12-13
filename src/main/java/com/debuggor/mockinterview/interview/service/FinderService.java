package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.async.MailTask;
import com.debuggor.mockinterview.common.constant.MailConstant;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.common.util.ActivateCodeUtil;
import com.debuggor.mockinterview.common.util.Md5Util;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.dao.FinderDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 求职者
 */
@Service
public class FinderService {

    @Autowired
    private FinderDao finderDao;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * 查询所有求职者
     *
     * @param finder
     * @param pn
     * @return
     */
    public PageInfo getFinderList(Finder finder, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Finder> finderList = finderDao.getFinderList(finder);
        PageInfo pageInfo = new PageInfo<>(finderList, PageConstant.Navigate_Pages);
        return pageInfo;
    }

    /**
     * 用户登录
     */
    public String login(Finder finder) {
        if (finder == null) {
            return MockConstant.LOGIN_ERROR;
        }
        Finder user = null;
        if (finder.getEmail() != null) {
            user = finderDao.getFinderByEmail(finder.getEmail());
        }
        if (user == null) {
            return MockConstant.LOGIN_ERROR;
        }
        String passwordMD5 = Md5Util.hash(finder.getPassword());
        if (!passwordMD5.equals(user.getPassword())) {
            return MockConstant.LOGIN_ERROR;
        }
        return MockConstant.LOGIN_SUCCESS;
    }

    public Finder getFinderByEmail(String email) {
        if (email == null) {
            return null;
        }
        Finder finder = finderDao.getFinderByEmail(email);
        return finder;
    }

    /**
     * 求职者注册
     *
     * @param finder
     * @param repassword
     * @return
     */
    public String register(Finder finder, String repassword) {
        if (finder == null || repassword == null) {
            return null;
        }
        if (!repassword.equals(finder.getPassword())) {
            return "两次密码不一致";
        }
        Finder finderByEmail = finderDao.getFinderByEmail(finder.getEmail());
        if (finderByEmail != null) {
            return "该邮箱已注册，换其他邮箱试试";
        }
        if (finder.getPassword() != null) {
            finder.setPassword(Md5Util.hash(finder.getPassword()));
        }
        finder.setHeadUrl("https://dn-qiniu-avatar.qbox.me/avatar/6219d3089bbb149606e87debb24ddbdd?qiniu-avatar");
        finder.setCreateTime(new Date());
        // 未激活
        finder.setIsActivate(0);
        String activateCode = ActivateCodeUtil.createActivateCode();
        finder.setActivateCode(activateCode);
        finderDao.insert(finder);
        taskExecutor.execute(new MailTask(activateCode, MailConstant.MAIL_FROM,
                finder.getEmail(), javaMailSender, MailConstant.REGISTERED_FINDER));
        return "ok";
    }

    /**
     * 求职者激活
     */
    public Integer isActivate(String code) {
        Integer affected = null;
        if (code != null) {
            affected = finderDao.updateActivate(code);
        }
        return affected;
    }

    public void update(Finder finder) {
        if (finder != null) {
            finderDao.update(finder);
        }
    }
}
