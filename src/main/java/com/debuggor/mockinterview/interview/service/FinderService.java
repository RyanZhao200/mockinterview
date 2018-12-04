package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.common.util.Md5Util;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.dao.FinderDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 求职者
 */
@Service
public class FinderService {

    @Autowired
    private FinderDao finderDao;

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
}
