package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.interview.bean.Evaluation;
import com.debuggor.mockinterview.interview.dao.EvaluationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 求职者对面试官 评价service层
 */
@Service
public class EvaluationService {

    @Autowired
    private EvaluationDao evaluationDao;

    /**
     * 插入一条评论
     *
     * @param evaluation
     */
    public Integer insert(Evaluation evaluation) {
        Integer eid = null;
        if (evaluation != null) {
            // 实际面试官的评分以10分为准；求职者做的评分是5分制度
            if (evaluation.getGrade() != null) {
                evaluation.setGrade(evaluation.getGrade() * 2);
            }
            eid = evaluationDao.insert(evaluation);
        }
        return eid;
    }
}
