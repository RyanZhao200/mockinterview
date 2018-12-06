package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinderDao {

    List<Finder> getFinderList(Finder finder);

    Finder getFinderByUserName(String username);

    /**
     * 通过邮箱获得用户信息
     *
     * @param email
     * @return
     */
    Finder getFinderByEmail(String email);

    /**
     * 插入求职者
     *
     * @param finder
     */
    void insert(Finder finder);

    /**
     * 跟新状态 激活
     *
     * @param code
     */
    Integer updateActivate(String code);

    /**
     * 跟新求职者
     *
     * @param finder
     */
    void update(Finder finder);
}
