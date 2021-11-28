package com.example.demo.common.util;


import lombok.extern.slf4j.Slf4j;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

@Slf4j
public class SendQQMailUtil {


    public static void main(String [] args) {
        SendQQMail("测试邮件","华为云demo服务器发送");
    }

    public static void SendQQMail(String title,String content){

        // 收件人电子邮箱
        String to = "zl736395078@icloud.com";

        // 发件人电子邮箱
        String from = "736395078@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("736395078@qq.com", "ruuvwjojsfqgbdfb"); //发件人邮件用户名、授权码
            }
        });
        if(session == null){
            SendQQMailUtil.log.info("Sent message error ,session is null");
            return ;
        }

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject(title);

            // 设置消息体
            message.setText(content);

            // 发送消息
            Transport.send(message);;
            SendQQMailUtil.log.info("Sent message successfully....from qq.com");
        }catch (MessagingException mex) {
            SendQQMailUtil.log.info("Sent message error Exception:" + mex.getMessage());
            mex.printStackTrace();
        }
    }

}
