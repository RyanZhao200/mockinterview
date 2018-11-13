package com.debuggor.mockinterview.common.dao;

import com.debuggor.mockinterview.common.bean.Admin;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AdminDao {

    Admin getAdminByUserName(String username);

    void updateAdminByUserName(Admin admin);

    Admin getAdminById(Integer id);
}
