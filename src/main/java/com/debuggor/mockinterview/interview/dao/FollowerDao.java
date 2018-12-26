package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Follower;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 关注表的dao层
 */
@Repository
public interface FollowerDao {

    /**
     * 插入一条关注信息
     *
     * @param follower
     */
    void insert(Follower follower);

    /**
     * 根据用户的ID，获取我关注的人
     *
     * @param uid
     * @param userType
     * @return
     */
    List<Follower> getFollowersByUid(@Param("uid") Integer uid, @Param("userType") String userType);

    /**
     * 根据用户ID，获取关注我的人
     *
     * @param uid
     * @param userType
     * @return
     */
    List<Follower> getFollowingByUid(@Param("uid") Integer uid, @Param("userType") String userType);

    /**
     * 更新关注记录（取消关注、拉黑）
     *
     * @param follower
     */
    void update(Follower follower);

    /**
     * 是否关注
     *
     * @param follower
     * @return
     */
    Follower isFollowedByUUid(Follower follower);
}
