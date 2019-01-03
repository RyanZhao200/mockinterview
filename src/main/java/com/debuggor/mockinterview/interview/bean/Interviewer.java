package com.debuggor.mockinterview.interview.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class Interviewer {
    // 面试官ID
    private Integer iid;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 昵称（已舍弃）
    private String nickname;
    // 邮箱
    private String email;
    // 头像链接
    private String headUrl;
    // 性别(1:女,0:男)
    private Integer sex;
    // 个人介绍
    private String description;
    // 创建时间
    private Date createTime;
    // 查询开始时间
    private String startTime;
    // 查询结束时间
    private String endTime;
    // 工作年限
    private Integer workYear;
    // 是否激活（1：激活，0：未激活）
    private Integer isActivate;
    // 激活码
    private String activateCode;
    // 是否认证（1：yes,0:no）
    private Integer isCertification;
    // 评分等级
    private Float grade;
    // 收费
    private String cost;
    // QQ
    private String qq;
    // 微信
    private String weixin;
    // 电话
    private String phone;
    // 博客地址
    private String blogUrl;
    // 面试官的类别（能面试哪些类别）
    private List<String> types;
    // IT类别ID（用作列表分类查询）
    private Integer tid;
    // 首页展示时的面试官类别
    private String typeName;
    //帮助人的数量
    private Integer helpPeopleNum;
    // 我关注的人的数量（不区分求职者和面试官）
    private Integer followingNum;
    //关注我的人的数量
    private Integer followersNum;
    /**
     * 做排序 null、综合；1、评分从高到低；2、价格从高到低；3、价格从低到高；
     * 4、帮助人数；5、工作年限
     */
    private Integer order;

}
