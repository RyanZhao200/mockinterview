package com.debuggor.mockinterview.common.dao;

import com.debuggor.mockinterview.common.bean.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息Dao
 */
@Repository
public interface MessageDao {
    /**
     * 插入一条消息
     *
     * @param message
     */
    void insert(Message message);

    /**
     * 更新消息
     *
     * @param message
     */
    void update(Message message);

    /**
     * 根据订单ID获取消息
     *
     * @param oid
     * @return
     */
    Message getMessageByOid(Integer oid);

    /**
     * 根据用户ID获取用户消息
     *
     * @param uid
     * @param userType
     * @return
     */
    List<Message> getMessageByUid(@Param("uid") Integer uid, @Param("userType") String userType);

    /**
     * 根据用户的ID，更新消息的状态为用户不可见
     *
     * @param message
     */
    void updateByUid(Message message);
}
