package com.xu.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    public static void sendMail(String content) {
        try {
            // final String username = "371698049";
            // final String password = "hxthefoevrpzbgdc";

            final String username = "2036994784";
            final String password = "fqslfgjipfjlchaa";

            Session session = getDefaultInstance(buildProperties(), username, password);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username + "@qq.com", "巴菲特"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("371698049@qq.com,519494051@qq.com", false));
            msg.setSubject("stock analyse result");
            msg.setText(content);
            msg.setContent(content, "text/html;charset=gbk");

            Transport.send(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties buildProperties() {
        Properties props = System.getProperties();

        props.setProperty("mail.smtp.host", "smtp.qq.com");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        return props;
    }

    private static Session getDefaultInstance(Properties props, final String username, final String password) {
        return Session.getDefaultInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
