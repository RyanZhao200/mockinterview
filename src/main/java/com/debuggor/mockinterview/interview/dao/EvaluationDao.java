package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Evaluation;
import org.springframework.stereotype.Repository;

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
}
