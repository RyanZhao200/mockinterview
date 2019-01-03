package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.interview.bean.Evaluation;
import com.debuggor.mockinterview.interview.dao.EvaluationDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

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

    /**
     * 根据面试官的ID，获取面试官的评论
     *
     * @param iid
     * @param pn
     * @return
     */
    public PageInfo getEvaluationByIid(Integer iid, Integer pn) {
        if (iid == null) {
            return null;
        }
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Evaluation> evaluations = evaluationDao.getEvaluationByIid(iid);
        PageInfo pageInfo = new PageInfo<>(evaluations, PageConstant.Navigate_Pages);
        return pageInfo;
    }

    /**
     * 根据面试官ID，获取面试官被评论条数
     *
     * @param iid
     * @return
     */
    public Integer getEvaluationNumByIid(Integer iid) {
        if (iid == null) {
            return 0;
        }
        Integer num = evaluationDao.getEvaluationNumByIid(iid);
        return num;
    }

    /**
     * 根据面试官ID，获取面试官的平均评分(小数)
     *
     * @param iid
     * @return
     */
    public Float getEvaluationGradeByIid(Integer iid) {
        if (iid == null) {
            return null;
        }
        Float grade = evaluationDao.getEvaluationGradeByIid(iid);
        if (grade == null) {
            return 0.0f;
        }
        DecimalFormat df = new DecimalFormat("0.0");
        return Float.valueOf(df.format(grade));
    }

    /**
     * 获得最近的热评
     */
    public void getRecentHotEvaluations(){

    }
}
