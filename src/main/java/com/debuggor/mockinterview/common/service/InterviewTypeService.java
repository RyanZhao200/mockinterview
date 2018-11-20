package com.debuggor.mockinterview.common.service;

import com.debuggor.mockinterview.common.dao.InterviewTypeDao;
import com.debuggor.mockinterview.interview.bean.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewTypeService {

    @Autowired
    private InterviewTypeDao interviewTypeDao;

    public List<Type> getTypeList() {
        return interviewTypeDao.getTypeList();
    }

    public List<Type> getTypeByParentId(Integer parentId) {
        List<Type> parentType = interviewTypeDao.getTypeByParentId(parentId);
        Type type = new Type();
        type.setTid(0);
        type.setTypeName("无父栏目");
        parentType.add(type);
        return parentType;
    }

    public void updateType(Type type) {
        if (type.getTid() != null) {
            interviewTypeDao.updateType(type);
        }
    }

    public void delete(Integer tid) {
        if (tid != null) {
            interviewTypeDao.delete(tid);
        }
    }

    public void insert(Type type) {
        interviewTypeDao.insert(type);
    }

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
}
