package com.debuggor.mockinterview.forum.dao;


import com.debuggor.mockinterview.forum.bean.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumTypeDao {

    List<Type> getTypeList();

    Type getTypeById(Integer tid);

    void insert(Type type);

    void updateType(Type type);

    void delete(Integer tid);
}
