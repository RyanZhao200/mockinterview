package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.enumerate.StatusEnum;
import com.debuggor.mockinterview.interview.bean.ChatInterview;
import com.debuggor.mockinterview.interview.dao.ChatInterviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatInterviewService {

    @Autowired
    private ChatInterviewDao chatInterviewDao;

    /**
     * 插入一条记录
     *
     * @param chat
     */
    public void insert(ChatInterview chat) {
        if (chat != null) {
            chat.setSendTime(new Date());
            chat.setMessageStatus(StatusEnum.NORMAL.key);
            chatInterviewDao.insert(chat);
        }
    }

    /**
     * 获得聊天记录
     *
     * @param chat
     * @return
     */
    public List<ChatInterview> getChatListForDouble(ChatInterview chat) {
        List<ChatInterview> chats = chatInterviewDao.getChatListForDouble(chat);
        return chats;
    }
}
