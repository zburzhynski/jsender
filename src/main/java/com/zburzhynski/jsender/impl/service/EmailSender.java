package com.zburzhynski.jsender.impl.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.repository.ISentMessageRepository;
import com.zburzhynski.jsender.api.repository.ISettingRepository;
import com.zburzhynski.jsender.api.service.ISender;
import com.zburzhynski.jsender.impl.domain.SentMessage;
import com.zburzhynski.jsender.impl.domain.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Email sender.
 * <p/>
 * Date: 02.08.2016
 *
 * @author Vladimir Zburzhynski
 */
@Service("emailSender")
@Transactional(readOnly = true)
public class EmailSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private Session session;

    @Autowired
    private ISentMessageRepository<String, SentMessage> sentMessageRepository;

    @Autowired
    private ISettingRepository<String, Setting> settingRepository;

    /**
     * Send email.
     *
     * @param email email to send
     * @return sending response
     */
    @Override
    @Transactional(readOnly = false)
    public Map<Recipient, String> send(Message email) {
        LOGGER.info("Sending email {} ", email);
        buildSession();
        Map<Recipient, String> response = new HashMap<>();
        for (Recipient recipient : email.getRecipients()) {
            String status;
            try {
                javax.mail.Message message = new MimeMessage(session);
                message.setFrom(isNotBlank(email.getFrom()) ? new InternetAddress(email.getFrom()) : null);
                message.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(recipient.getContactInfo()));
                message.setSubject(isNotBlank(email.getSubject()) ? email.getSubject() : null);
                message.setText(isNotBlank(email.getText()) ? email.getText() : null);
                Transport.send(message);
                status = "Email sent successfully";
                LOGGER.info("Email sent successfully, recipient = " + recipient.getContactInfo());
            } catch (MessagingException e) {
                status = e.getClass().getName();
                LOGGER.error("An error occurred while sending email", e);
            }
            SentMessage sentMessage = new SentMessage();
            sentMessage.setSentDate(new Date());
            sentMessage.setClientId(recipient.getClientId());
            sentMessage.setContactInfo(recipient.getContactInfo());
            sentMessage.setSubject(email.getSubject());
            sentMessage.setText(email.getText());
            sentMessage.setStatus(status);
            sentMessage.setSendingType(SendingType.EMAIL);
            sentMessageRepository.saveOrUpdate(sentMessage);
            response.put(recipient, status);
        }
        return response;
    }

    private void buildSession() {
        String smtpHost = settingRepository.findByName(Settings.MAIL_SMTP_HOST).getValue();
        String smtpPort = settingRepository.findByName(Settings.MAIL_SMTP_PORT).getValue();
        final String userName = settingRepository.findByName(Settings.MAIL_USER_NAME).getValue();
        final String password = settingRepository.findByName(Settings.MAIL_PASSWORD).getValue();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
    }

}
