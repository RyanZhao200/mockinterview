package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Evaluation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评价Dao
 */
@Repository
public interface EvaluationDao {

    /**
     * 插入一条评论
     *
     * @param evaluation
     */
    Integer insert(Evaluation evaluation);

    /**
     * 根据面试官的ID获取面试官的评论
     *
     * @param iid
     * @return
     */
    List<Evaluation> getEvaluationByIid(Integer iid);
}
