package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.enumerate.ChatMessageStatusEnum;
import com.debuggor.mockinterview.interview.bean.ChatSession;
import com.debuggor.mockinterview.interview.dao.ChatSessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatSessionService {

    @Autowired
    private ChatSessionDao chatSessionDao;

    /**
     * 插入一条消息
     *
     * @param chatSession
     */
    public void insert(ChatSession chatSession) {
        chatSession.setSendTime(new Date());
        chatSession.setSendStatus(ChatMessageStatusEnum.SEND.key);
        chatSession.setAcceptStatus(ChatMessageStatusEnum.NOT_ACCEPT.key);
        chatSessionDao.insert(chatSession);
    }

    /**
     * 根据双方信息，得到双方的聊天信息
     *
     * @param chatSession
     * @return
     */
    public List<ChatSession> get(ChatSession chatSession) {
        List<ChatSession> chatSessions = chatSessionDao.getChatSessionsByDoubleUid(chatSession);
        return chatSessions;
    }
}
