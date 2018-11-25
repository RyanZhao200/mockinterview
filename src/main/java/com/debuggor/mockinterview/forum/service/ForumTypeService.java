package com.debuggor.mockinterview.forum.service;

import com.debuggor.mockinterview.forum.bean.Type;
import com.debuggor.mockinterview.forum.dao.ForumTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumTypeService {

    @Autowired
    private ForumTypeDao forumTypeDao;

    public List<Type> getForumTypeList() {
        List<Type> typeList = forumTypeDao.getTypeList();
        return typeList;
    }

    public void delete(Integer tid) {
        if (tid != null) {
            forumTypeDao.delete(tid);
        }
    }

    public void insert(Type type) {
        forumTypeDao.insert(type);
    }

    public Type getTypeById(Integer tid) {
        Type type = null;
        if (tid != null) {
            type = forumTypeDao.getTypeById(tid);
        }
        return type;
    }

    public void updateType(Type type) {
        if (type != null) {
            forumTypeDao.updateType(type);
        }
    }
}
