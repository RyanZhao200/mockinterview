package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Interviewer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewerDao {

    List<Interviewer> getInterviewerList(Interviewer interviewer);

    /**
     * 首页展示所用；根据父类型ID查询评分最高的4条记录
     *
     * @return
     */
    List<Interviewer> getInterviewerListIndexByTid(Integer tid);
}
