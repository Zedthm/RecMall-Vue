package com.recMall.common.utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {
    private static JavaMailSender mailSender;

    // 初始化配置（从配置文件读取）
    public static void initConfig(String host, int port, 
                                String username, String password,
                                boolean ssl) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        
        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        if (ssl) {
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.socketFactory.class", 
                    "javax.net.ssl.SSLSocketFactory");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }
        mailSender = sender;
    }
    private static final String USER = "3013419596@qq.com"; // 发件人邮箱
    private static final String PASSWORD = "pgoqkriszczjdgii"; // 授权码
    public static void sendEMail(String to, String text, String title) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.163.com");
            props.put("mail.user", USER);
            props.put("mail.password", PASSWORD);

            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USER, PASSWORD);
                }
            };

            Session mailSession = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(USER));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(title);
            message.setContent(text, "text/html;charset=UTF-8");

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}