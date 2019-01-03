package com.debuggor.mockinterview.common.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Admin {

    // 管理员ID
    private Integer aid;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 邮箱
    private String email;
    // 头像链接
    private String headUrl;
    // 创建时间
    private Date createTime;


}
