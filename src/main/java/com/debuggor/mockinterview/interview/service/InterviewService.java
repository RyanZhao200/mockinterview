package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.bean.vo.InterviewerVo;
import com.debuggor.mockinterview.common.constant.PageConstant;
import com.debuggor.mockinterview.common.dao.InterviewTypeDao;
import com.debuggor.mockinterview.interview.bean.Interviewer;
import com.debuggor.mockinterview.interview.bean.Type;
import com.debuggor.mockinterview.interview.dao.FlowDao;
import com.debuggor.mockinterview.interview.dao.InterviewDao;
import com.debuggor.mockinterview.interview.dao.InterviewerDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.length;

/**
 * 面试相关service层
 */
@Service
public class InterviewService {

    @Autowired
    private InterviewDao interviewDao;
    @Autowired
    private InterviewTypeDao interviewTypeDao;
    @Autowired
    private InterviewerDao interviewerDao;
    @Autowired
    private FlowDao flowDao;

    /**
     * 面试官列表
     *
     * @param interviewer
     * @param pn
     * @return
     */
    public PageInfo getInterviewerList(Interviewer interviewer, Integer pn) {
        PageHelper.startPage(pn, PageConstant.Page_Sizes);
        List<Interviewer> interviewerList = interviewDao.getInterviewerList(interviewer);
        PageInfo pageInfo = new PageInfo<>(interviewerList, PageConstant.Navigate_Pages);
        // 获取每个面试官所能面试的类别
        List<Interviewer> interviewers = new ArrayList<>();
        for (Interviewer iv : interviewerList) {
            List<String> interviewTypes = interviewDao.getInterviewTypes(iv.getIid());
            iv.setTypes(interviewTypes);
            Integer helpPeopleNum = flowDao.getFlowNumByIid(iv.getIid());
            iv.setHelpPeopleNum(helpPeopleNum);
            interviewers.add(iv);
        }
        pageInfo.setList(interviewers);
        return pageInfo;
    }

    /**
     * 首页展示；获取每个类别的几位面试官信息
     *
     * @return
     */
    public List<InterviewerVo> getInterviewerIndexList() {
        List<InterviewerVo> interviewerVoList = new ArrayList<>();
        // 每个类别展示四位面试官
        List<Type> parentTypes = interviewTypeDao.getTypeByParentId(0);
        for (Type type : parentTypes) {
            InterviewerVo interviewerVo = new InterviewerVo();
            interviewerVo.setTid(type.getTid());
            interviewerVo.setTypeName(type.getTypeName());
            List<Interviewer> interviewers = interviewDao.getInterviewerListIndexByTid(type.getTid());
            interviewerVo.setInterviewers(interviewers);

            interviewerVoList.add(interviewerVo);
        }
        return interviewerVoList;
    }

    /**
     * 根据面试官ID，获取面试官的详情
     *
     * @param iid
     * @return
     */
    public Interviewer getInterviewerById(Integer iid) {
        Interviewer interviewer = interviewerDao.getInterviewerById(iid);
        if (interviewer != null) {
            List<String> interviewTypes = interviewDao.getInterviewTypes(interviewer.getIid());
            Integer helpPeopleNum = flowDao.getFlowNumByIid(interviewer.getIid());
            interviewer.setTypes(interviewTypes);
            interviewer.setHelpPeopleNum(helpPeopleNum);
        }
        return interviewer;
    }

    /**
     * 热门面试官（ 在面试官详情页右侧展示 8位面试官）
     */
    public List<Interviewer> getInterviewerHot(Integer num) {
        List<Interviewer> interviewers = new ArrayList<>();
        List<Interviewer> list = interviewerDao.getInterviewerHot(num);
        for (Interviewer interviewer : list) {
            List<String> interviewTypes = null;
            if (interviewer != null) {
                interviewTypes = interviewDao.getInterviewTypes(interviewer.getIid());
            }
            interviewer.setTypes(interviewTypes);
            interviewers.add(interviewer);
        }
        return interviewers;
    }
}
