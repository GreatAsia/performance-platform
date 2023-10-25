package com.okay.testcenter.tools;


import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 发送邮件Util
 */
@Component
public class MailUtil {

    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private static String mailServerHost;
    private static String mailSenderAddress;
    private static String mailSenderNick;
    private static String mailSenderUsername;
    private static String mailSenderPassword;

    @Value("${email.mail.server.host}")
    public void setMailServerHost(String serverHost) {
        mailServerHost = serverHost;
    }

    @Value("${email.mail.sender.address}")
    public void setMailSenderAddress(String senderAddress) {
        mailSenderAddress = senderAddress;
    }

    @Value("${email.mail.sender.nick}")
    public void setMailSenderNick(String senderNick) {
        mailSenderNick = senderNick;
    }

    @Value("${email.mail-sender.username}")
    public void setMailSenderUsername(String senderUsername) {
        mailSenderUsername = senderUsername;
    }

    @Value("${email.mail-sender.password}")
    public void setMailSenderPassword(String senderPassword) {
        mailSenderPassword = senderPassword;
    }

    /**
     * 发送 邮件方法 (Html格式，支持附件)
     *
     * @return void
     */
    public static Boolean sendEmail(MailInfo mailInfo) {

        try {
            HtmlEmail email = new HtmlEmail();
            // 配置信息
            email.setHostName(mailServerHost);
            email.setFrom(mailSenderAddress, mailSenderNick);
            email.setAuthentication(mailSenderUsername, mailSenderPassword);
            email.setCharset("UTF-8");
            email.setSubject(mailInfo.getSubject());
            email.setHtmlMsg(mailInfo.getContent());

            // 添加附件
            List<EmailAttachment> attachments = mailInfo.getAttachments();
            if (null != attachments && attachments.size() > 0) {
                for (int i = 0; i < attachments.size(); i++) {
                    email.attach(attachments.get(i));
                }
            }

            // 收件人
            List<String> toAddress = mailInfo.getToAddress();
            if (null != toAddress && toAddress.size() > 0) {
                for (int i = 0; i < toAddress.size(); i++) {
                    email.addTo(toAddress.get(i));
                }
            }
            // 抄送人
            List<String> ccAddress = mailInfo.getCcAddress();
            if (null != ccAddress && ccAddress.size() > 0) {
                for (int i = 0; i < ccAddress.size(); i++) {
                    email.addCc(ccAddress.get(i));
                }
            }
            //邮件模板 密送人
            List<String> bccAddress = mailInfo.getBccAddress();
            if (null != bccAddress && bccAddress.size() > 0) {
                for (int i = 0; i < bccAddress.size(); i++) {
                    email.addBcc(ccAddress.get(i));
                }
            }
            email.send();
            logger.info("邮件发送成功!!!");
        } catch (EmailException e) {
            logger.error("发送邮件失败!!!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
