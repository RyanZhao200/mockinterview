package com.debuggor.mockinterview.interview.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Finder {
    /**
     * 求职者ID
     */
    private Integer fid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称---- 已经舍弃-------
     */
    private String nickname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像链接
     */
    private String headUrl;
    /**
     * 签名
     */
    private String signature;
    /**
     * 性别 1:女,0:男
     */
    private Integer sex;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否激活（1：yes）
     */
    private Integer isActivate;
    /**
     * 激活码
     */
    private String activateCode;
    /**
     * QQ
     */
    private String qq;
    /**
     * 微信
     */
    private String weixin;
    /**
     * 电话
     */
    private String phone;
    /**
     * 我关注的人的数量（不区分求职者和面试官）
     */
    private Integer followingNum;
    /**
     * 关注我的人的数量
     */
    private Integer followersNum;

}
