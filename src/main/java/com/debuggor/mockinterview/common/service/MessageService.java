package com.debuggor.mockinterview.common.service;

import com.debuggor.mockinterview.common.bean.Message;
import com.debuggor.mockinterview.common.dao.MessageDao;
import com.debuggor.mockinterview.common.enumerate.MessageStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 消息service层
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    /**
     * 插入一条消息
     *
     * @param message
     */
    public void insert(Message message) {
        messageDao.insert(message);
    }

    /**
     * 更新消息
     *
     * @param message
     */
    public void update(Message message) {
        messageDao.update(message);
    }

    /**
     * 根据订单ID获取消息
     *
     * @param oid
     * @param userType
     * @return
     */
    public Message getMessageByOid(Integer oid, String userType) {
        Message message = null;
        if (oid != null && userType != null) {
            message = messageDao.getMessageByOid(oid,userType);
        }
        return message;
    }

    /**
     * 根据用户的ID获取消息
     *
     * @param uid
     * @param userType
     * @return
     */
    public List<Message> getMessageByUid(Integer uid, String userType) {
        List<Message> messages = null;
        if (uid != null && userType != null) {
            messages = messageDao.getMessageByUid(uid, userType);
        }
        return messages;
    }

    /**
     * 根据用户ID，更新用户的所有消息的状态
     *
     * @param uid
     * @param userType
     */
    public void updateByUid(Integer uid, String userType) {
        if (uid != null) {
            Message message = new Message();
            message.setUid(uid);
            message.setUserType(userType);
            message.setMessageStatus(MessageStatusEnum.DELETE.key);
            message.setUpdateTime(new Date());
            messageDao.updateByUid(message);
        }
    }
}
