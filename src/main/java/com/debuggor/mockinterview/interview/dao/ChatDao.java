package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Chat;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天dao
 */
@Repository
public interface ChatDao {
    /**
     * 插入一条聊天记录
     *
     * @param chat
     */
    void insert(Chat chat);

    /**
     * 得到求职者A和面试官B的聊天记录
     *
     * @param chat
     * @return
     */
    List<Chat> getChatListForDouble(Chat chat);
}
