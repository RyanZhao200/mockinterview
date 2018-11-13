package com.debuggor.mockinterview.common.service;

import com.debuggor.mockinterview.common.bean.Admin;
import com.debuggor.mockinterview.common.constant.MockConstant;
import com.debuggor.mockinterview.common.dao.AdminDao;
import com.debuggor.mockinterview.common.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Admin getAdminById(Integer id) {
        if (id != null) {
            return adminDao.getAdminById(id);
        } else {
            return null;
        }
    }


    public void updateAdmin(Admin admin) {
        adminDao.updateAdminByUserName(admin);
    }

    public Admin getAdminByUserName(String username) {
        if (username == null || "".equals(username)) {
            return null;
        }
        return adminDao.getAdminByUserName(username);
    }

    public String login(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        if (username == null || password == null) {
            return MockConstant.LOGIN_ERROR;
        }
        Admin admin1 = adminDao.getAdminByUserName(username);
        if (admin1 == null) {
            return MockConstant.LOGIN_ERROR;
        }
        String passwordMd5 = Md5Util.hash(password);
        if (!passwordMd5.equals(admin1.getPassword())) {
            return MockConstant.LOGIN_ERROR;
        }
        return MockConstant.LOGIN_SUCCESS;
    }
}
