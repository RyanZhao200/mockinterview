package com.debuggor.mockinterview.common.dao;

import com.debuggor.mockinterview.common.bean.Admin;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AdminDao {
    /**
     * 根据用户名获得用户信息
     *
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 跟新管理员信息
     *
     * @param admin
     */
    void updateAdminByUserName(Admin admin);

    /**
     * 根据ID获得管理员信息
     *
     * @param id
     * @return
     */
    Admin getAdminById(Integer id);
}
