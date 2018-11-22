package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Interviewer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewerDao {

    List<Interviewer> getInterviewerList(Interviewer interviewer);
}
