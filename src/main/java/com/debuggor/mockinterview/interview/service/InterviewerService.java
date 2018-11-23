package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewerService {

    @Autowired
    private InterviewerDao interviewerDao;

    public PageInfo getInterviewerList(Interviewer interviewer, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Interviewer> interviewerList = interviewerDao.getInterviewerList(interviewer);
        PageInfo pageInfo = new PageInfo<>(interviewerList, PageConstant.Navigate_Pages);
        return pageInfo;
    }
}
