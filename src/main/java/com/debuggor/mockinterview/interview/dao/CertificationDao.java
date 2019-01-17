package com.debuggor.mockinterview.interview.dao;

import com.debuggor.mockinterview.interview.bean.Certification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationDao {
    /**
     * 根据面试官的ID，获取面试官最近认证的信息
     *
     * @param iid
     * @return
     */
    Certification getLastCertificationByiid(Integer iid);

    /**
     * 插入面试官的认证信息
     *
     * @param certification
     */
    void insert(Certification certification);

    /**
     * 更新面试官认证的消息；成功、失败
     *
     * @param certification
     */
    void update(Certification certification);

    /**
     * 获得面试官认证信息列表
     *
     * @param certification
     * @return
     */
    List<Certification> getCertificationList(Certification certification);

    /**
     * 根据用户ID，得到面试官所有记录的认证信息
     *
     * @param iid
     * @return
     */
    Certification getCertificationsByiid(Integer iid);

}
