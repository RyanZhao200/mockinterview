package com.debuggor.mockinterview.common.async;

import com.debuggor.mockinterview.common.constant.MailConstant;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

/**
 * 发送邮件
 */
public class MailTask implements Runnable {
    // 激活码
    private String code;
    // 发送方邮件
    private String fromEmail;
    //接收方邮件
    private String toEmail;
    // 发送邮件
    private JavaMailSender javaMailSender;
    // 操作
    private int operation;
    // 内容
    private String content;

    /**
     * 通信用的构造函数
     *
     * @param fromEmail
     * @param toEmail
     * @param javaMailSender
     * @param operation
     */
    public MailTask(String fromEmail, String toEmail, JavaMailSender javaMailSender, String content, int operation) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.javaMailSender = javaMailSender;
        this.content = content;
        this.operation = operation;
    }

    /**
     * 注册、重置密码用的构造函数
     *
     * @param code
     * @param fromEmail
     * @param toEmail
     * @param javaMailSender
     * @param operation
     */
    public MailTask(String code, String fromEmail, String toEmail, JavaMailSender javaMailSender, int operation) {
        this.code = code;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.javaMailSender = javaMailSender;
        this.operation = operation;
    }

    @Override
    public void run() {
        javaMailSender.send(new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                System.out.println("开始发送邮件...");
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setFrom(fromEmail);
                mimeMessageHelper.setTo(toEmail);
                StringBuilder sb = new StringBuilder();
                sb.append("<html><head></head><body>");

                if (operation == 1) {
                    mimeMessageHelper.setSubject("[IT模拟面试平台] 邮箱激活通知");
                    sb.append("尊敬的" + toEmail + "，您好,感谢您使用IT模拟面试平台。<br><br>");
                    sb.append("请点击以下链接进行邮箱验证，以便开始使用您的账户：<br>");
                    sb.append("<a href=" + MailConstant.DOMAIN_NAME + "finder/activate?code=" + code);
                    sb.append(">点击激活</a>");
                    sb.append("<br>如果您并未申请IT模拟面试平台账户，可能是其他用户误输入了您的邮箱地址。请忽略此邮件，或者联系我们。<br>");
                } else if (operation == 2) {
                    mimeMessageHelper.setSubject("[IT模拟面试平台] 邮箱激活通知");
                    sb.append("尊敬的" + toEmail + "，您好,感谢您使用IT模拟面试平台。注册成为一名面试官！<br><br>");
                    sb.append("请点击以下链接进行邮箱验证，以便开始使用您的账户：<br>");
                    sb.append("<a href=" + MailConstant.DOMAIN_NAME + "interviewer/activate?code=" + code);
                    sb.append(">点击激活</a>");
                    sb.append("<br>如果您并未申请IT模拟面试平台账户，可能是其他用户误输入了您的邮箱地址。请忽略此邮件，或者联系我们。<br>");
                } else if (operation == 3) {
                    mimeMessageHelper.setSubject("[IT模拟面试平台] 请确认修改您的密码");
                } else if (operation == 4) {
                    mimeMessageHelper.setSubject("您在IT模拟面试平台的小伙伴给你发消息啦");
                    sb.append(content);
                }

                sb.append("<br><br><br>");
                sb.append("华侨大学厦门校区-IT模拟面试平台-工单支持<br>");
                sb.append("Email：debuggor@foxmail.com");
                sb.append("<br><br>");
                sb.append("</body></html>");
                mimeMessageHelper.setText(sb.toString(), true);
                System.out.println("结束发邮件...");
            }
        });
    }
}
