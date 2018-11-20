package com.debuggor.mockinterview.common.dao;

import com.debuggor.mockinterview.interview.bean.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewTypeDao {

    List<Type> getTypeList();

    Type getTypeById(Integer tid);

    void insert(Type type);

    void updateType(Type type);

    List<Type> getTypeByParentId(Integer parentId);

    void delete(Integer tid);
}
