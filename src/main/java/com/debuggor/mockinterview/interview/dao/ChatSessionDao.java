package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.ChatSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatSessionDao {
    /**
     * 插入一条聊天消息到数据库
     *
     * @param chatSession
     */
    void insert(ChatSession chatSession);

    /**
     * 根据双方用户的信息获得聊天记录详情
     *
     * @param chatSession
     * @return
     */
    List<ChatSession> getChatSessionsByDoubleUid(ChatSession chatSession);

    /**
     * 更新消息；用户是否删除
     *
     * @param chatSession
     */
    void update(ChatSession chatSession);
}
