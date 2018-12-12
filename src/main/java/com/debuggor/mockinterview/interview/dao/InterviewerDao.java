package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Interviewer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewerDao {

    /***
     * 得到面试官列表
     * @param interviewer
     * @return
     */
    List<Interviewer> getInterviewerList(Interviewer interviewer);

    /**
     * 首页展示所用；根据父类型ID查询评分最高的4条记录
     *
     * @return
     */
    List<Interviewer> getInterviewerListIndexByTid(Integer tid);

    /**
     * 根据面试官的ID，获取面试官所能面试的类别
     *
     * @param iid
     * @return
     */
    List<String> getInterviewTypes(Integer iid);
}
