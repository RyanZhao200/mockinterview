package com.debuggor.mockinterview.interview.service;

import com.debuggor.mockinterview.common.enumerate.StatusEnum;
import com.debuggor.mockinterview.interview.bean.Chat;
import com.debuggor.mockinterview.interview.dao.ChatDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatDao chatDao;

    /**
     * 插入一条记录
     *
     * @param chat
     */
    public void insert(Chat chat) {
        if (chat != null) {
            chat.setSendTime(new Date());
            chat.setMessageStatus(StatusEnum.NORMAL.key);
            chatDao.insert(chat);
        }
    }

    /**
     * 获得聊天记录
     *
     * @param chat
     * @return
     */
    public List<Chat> getChatListForDouble(Chat chat) {
        List<Chat> chats = chatDao.getChatListForDouble(chat);
        return chats;
    }
}
