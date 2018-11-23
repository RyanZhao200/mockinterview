package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Finder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinderDao {

    List<Finder> getFinderList(Finder finder);
}
