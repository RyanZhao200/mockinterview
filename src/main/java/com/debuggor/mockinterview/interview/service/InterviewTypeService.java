package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.interview.dao.InterviewTypeDao;
import com.debuggor.mockinterview.interview.bean.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterviewTypeService {

    @Autowired
    private InterviewTypeDao interviewTypeDao;

    /**
     * 得到IT类别列表
     *
     * @return
     */
    public List<Type> getTypeList() {
        return interviewTypeDao.getTypeList();
    }

    /**
     * @param parentId
     * @return
     */
    public List<Type> getTypeByParentId(Integer parentId) {
        List<Type> parentType = interviewTypeDao.getTypeByParentId(parentId);
        Type type = new Type();
        type.setTid(0);
        type.setTypeName("无父栏目");
        parentType.add(type);
        return parentType;
    }

    /**
     * 更新IT类别
     *
     * @param type
     */
    public void updateType(Type type) {
        if (type.getTid() != null) {
            interviewTypeDao.updateType(type);
        }
    }

    /**
     * 删除IT类别
     *
     * @param tid
     */
    public void delete(Integer tid) {
        if (tid != null) {
            interviewTypeDao.delete(tid);
        }
    }

    /**
     * 新增IT类别
     *
     * @param type
     */
    public void insert(Type type) {
        interviewTypeDao.insert(type);
    }

    /**
     * 通过ID得到IT类别信息
     *
     * @param tid
     * @return
     */
    public Type getTypeById(Integer tid) {
        Type type = interviewTypeDao.getTypeById(tid);
        // 非父栏目，则得到其父栏目
        if (type.getParentId() != 0 && type.getParentId() != null) {
            Type parent = interviewTypeDao.getTypeById(type.getParentId());
            type.setParentName(parent.getTypeName());
        } else {
            type.setParentName("无父栏目");
        }
        return type;
    }

    /**
     * 得到所有的父栏目，并且父栏目里面包含子栏目
     *
     * @return
     */
    public List<Type> getInterviewTypeList() {
        // 得到所有的父栏目
        List<Type> parentType = interviewTypeDao.getTypeByParentId(0);
        List<Type> typeList = new ArrayList<>();
        for (Type parent : parentType) {
            List<Type> tem = interviewTypeDao.getTypeByParentId(parent.getTid());
            parent.setChildTypes(tem);
            typeList.add(parent);
        }
        return typeList;
    }
}
