package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewTypeDao {
    /**
     * 得到IT分类列表
     *
     * @return
     */
    List<Type> getTypeList();

    /**
     * 根据ID获得IT分类信息
     *
     * @param tid
     * @return
     */
    Type getTypeById(Integer tid);

    /**
     * 新增一个IT分类
     *
     * @param type
     */
    void insert(Type type);

    /**
     * 更新IT分类
     *
     * @param type
     */
    void updateType(Type type);

    /**
     * 根据父ID得到子IT分类列表
     *
     * @param parentId
     * @return
     */
    List<Type> getTypeByParentId(Integer parentId);

    /**
     * 删除一个IT分类
     *
     * @param tid
     */
    void delete(Integer tid);
}
