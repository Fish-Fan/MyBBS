package com.fanyank.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Created by yanfeng-mac on 2017/4/11.
 */
public class EmailUtil {
    public static void sendHtmlEmail(String subject,String content,String toAddress) {
        HtmlEmail email = new HtmlEmail();

        //设置邮件服务器地址
        email.setHostName(ConfigProp.get("mail.smtp"));
        email.setAuthentication(ConfigProp.get("mail.username"),ConfigProp.get("mail.password"));
        email.setCharset(ConfigProp.get("mail.encoding"));
        email.setStartTLSEnabled(true);

        try {
            email.setFrom(ConfigProp.get("mail.from"));
            email.setSubject(subject);
            email.setHtmlMsg(content);

            email.addTo(toAddress);
            email.send();

        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
