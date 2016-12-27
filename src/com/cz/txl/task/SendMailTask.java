
package com.cz.txl.task;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class SendMailTask {
    /**
     * 发送邮件
     * @param to
     *   收信人
     * @param subject
     *   邮件主题
     * @param content
     *   邮件内容
     * @throws IOException
     * @throws AddressException
     * @throws MessagingException
     */
    public static void send_email(String to, String subject, String content)
            throws IOException, AddressException, MessagingException {
        Properties properties = new Properties();
        InputStream resourceAsStream = null;
        try {
            // 获取配置文件
            resourceAsStream = SendMailTask.class.getClassLoader().getResourceAsStream("email.properties");
            properties.load(resourceAsStream);
        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }
        // System.err.println("properties:"+properties);
        properties.put("mail.smtp.host", properties.get("mail.smtp.host"));
        properties.put("mail.smtp.port", properties.get("mail.smtp.port"));
        properties.put("mail.smtp.auth", "true");
        Authenticator authenticator = new Email_Authenticator(properties.get(
                "username").toString(), properties.get("password").toString());
        javax.mail.Session sendMailSession = javax.mail.Session
                .getInstance(properties, authenticator);
        MimeMessage mailMessage = new MimeMessage(sendMailSession);
        //设置发信人
        mailMessage.setFrom(new InternetAddress(properties.get("username")
                .toString(),"***"));
        // 设置收信人,Message.RecipientType.TO 收信人，Message.RecipientType.CC抄送人
        mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(
                to,properties.get("username").toString()));
        //主题
        mailMessage.setSubject(subject, "UTF-8");
        //设置邮件发送日期
        mailMessage.setSentDate(new Date());
        // MiniMultipart类是一个容器类
        Multipart mainPart = new MimeMultipart();
        // 创建一个邮件体对象
        BodyPart html = new MimeBodyPart();
        html.setContent(content.trim(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        mailMessage.setContent(mainPart);
        Transport.send(mailMessage);
    }
}
//通过构造函数传入身份验证信息
class Email_Authenticator extends Authenticator {
    String userName = null;
    String password = null;
    public Email_Authenticator() {
    }
    public Email_Authenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}