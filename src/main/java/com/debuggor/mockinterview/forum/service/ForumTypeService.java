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

    /**
     * 得到栏目列表
     *
     * @return
     */
    public List<Type> getForumTypeList() {
        List<Type> typeList = forumTypeDao.getTypeList();
        return typeList;
    }

    /**
     * 删除栏目
     *
     * @param tid
     */
    public void delete(Integer tid) {
        if (tid != null) {
            forumTypeDao.delete(tid);
        }
    }

    /**
     * 新增栏目，对应帖子数为0
     *
     * @param type
     */
    public void insert(Type type) {
        type.setPostNum(0);
        forumTypeDao.insert(type);
    }

    /**
     * 根据ID，得到对应栏目信息
     *
     * @param tid
     * @return
     */
    public Type getTypeById(Integer tid) {
        Type type = null;
        if (tid != null) {
            type = forumTypeDao.getTypeById(tid);
        }
        return type;
    }

    /**
     * 跟新栏目信息
     *
     * @param type
     */
    public void updateType(Type type) {
        if (type != null) {
            forumTypeDao.updateType(type);
        }
    }
}
