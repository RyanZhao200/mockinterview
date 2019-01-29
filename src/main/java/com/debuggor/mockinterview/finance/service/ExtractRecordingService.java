package com.debuggor.mockinterview.finance.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.finance.bean.ExtractRecording;
import com.debuggor.mockinterview.finance.bean.RechargeRecording;
import com.debuggor.mockinterview.finance.dao.ExtractRecordingDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExtractRecordingService {

    @Autowired
    private ExtractRecordingDao extractRecordingDao;

    /**
     * 提现记录
     *
     * @param pn
     * @return
     */
    public PageInfo<ExtractRecording> getExtractRecordings(Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<ExtractRecording> recordings = extractRecordingDao.getExtractRecordings();
        PageInfo<ExtractRecording> pageInfo = new PageInfo<>(recordings, PageConstant.Navigate_Pages);
        return pageInfo;
    }

    /**
     * 插入一条提现记录
     *
     * @param extractRecording
     */
    public void insert(ExtractRecording extractRecording) {
        extractRecording.setCreateTime(new Date());
        extractRecordingDao.insert(extractRecording);
    }
}
