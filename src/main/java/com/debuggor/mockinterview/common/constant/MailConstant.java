package com.debuggor.mockinterview.common.constant;

/**
 * 发邮件常数配置
 */
public class MailConstant {
    /**
     * 发送邮件的邮箱，要与application.properties中的一致
     */
    public static final String MAIL_FROM = "debuggor@163.com";
    /**
     * 域名
     */
    public static final String DOMAIN_NAME = "http://localhost:8080/";
    /**
     * 注册邮箱操作
     */
    public static final int REGISTERED = 1;
    /**
     * 重置密码
     */
    public static final int RESET_PASSWORD = 2;
    /**
     * 邮件通信
     */
    public static final int COMMUNICATION = 3;
}
