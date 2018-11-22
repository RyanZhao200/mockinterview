package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewerService {

    @Autowired
    private InterviewerDao interviewerDao;

    public List<Interviewer> getInterviewerList(Interviewer interviewer) {
        List<Interviewer> interviewerList = interviewerDao.getInterviewerList(interviewer);
        return interviewerList;
    }
}
