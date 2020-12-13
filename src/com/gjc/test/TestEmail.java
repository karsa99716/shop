package com.gjc.test;

import com.gjc.utils.EmailUtils;

public class TestEmail {
    public static void main(String[] args){
        EmailUtils send = new EmailUtils();
        send.sendActivateMail("liu2393329656@163.com","123");
//        try {
//            HtmlEmail email = new HtmlEmail();
//            //设置发邮件的用户名和密码（授权密码）
//            email.setAuthentication("2393329656","password");
//            //设置发送邮件服务器（SMTP服务器）域名
//            email.setHostName("smtp.qq.com");
//            //设置收件人邮箱
//            email.addTo("liu2393329656@163.com");
//            //设置发件人邮箱
//            email.setFrom("2393329656@qq.com");
//            //邮件的主题
//            email.setSubject("Test");
//            //设置编码方式，为了防止中文乱码
//            email.setCharset("GB2312");
//            //邮件的内容
//            email.setHtmlMsg("Test");
//
//            //发送邮件
//            email.send();
//        } catch (EmailException e) {
//            throw new RuntimeException(e);
//        }
    }
}
