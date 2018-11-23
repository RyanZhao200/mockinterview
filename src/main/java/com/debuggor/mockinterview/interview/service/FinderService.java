package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.interview.bean.Finder;
import com.debuggor.mockinterview.interview.dao.FinderDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
