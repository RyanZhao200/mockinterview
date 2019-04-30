package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.ChatInterview;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天dao
 */
@Repository
public interface ChatInterviewDao {
    /**
     * 插入一条聊天记录
     *
     * @param chat
     */
    void insert(ChatInterview chat);

    /**
     * 得到求职者A和面试官B的聊天记录
     *
     * @param chat
     * @return
     */
    List<ChatInterview> getChatListForDouble(ChatInterview chat);
}
