package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Interviewer;
import org.springframework.stereotype.Repository;

/**
 * 面试官dao层
 */
@Repository
public interface InterviewerDao {
    /**
     * 根据邮箱，获取面试官信息；用邮箱唯一标示面试官
     *
     * @param email
     * @return
     */
    Interviewer getInterviewerByEmail(String email);
}
