package com.debuggor.mockinterview.common.bean.vo;

import com.debuggor.mockinterview.interview.bean.Interviewer;

import java.util.List;

/**
 * 对interviewer进行包装，方便首页进行展示
 */
public class InterviewerVo {
    private String typeName;
    private List<Interviewer> interviewers;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Interviewer> getInterviewers() {
        return interviewers;
    }

    public void setInterviewers(List<Interviewer> interviewers) {
        this.interviewers = interviewers;
    }
}
