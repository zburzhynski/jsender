package com.zburzhynski.jsender.impl.service;

import static com.zburzhynski.jsender.api.domain.CommonConstant.COMMA;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.repository.ISettingRepository;
import com.zburzhynski.jsender.api.service.ISender;
import com.zburzhynski.jsender.impl.domain.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;
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
    private ISettingRepository<String, Setting> settingRepository;

    /**
     * Send email.
     *
     * @param email email to send
     * @return true if sending is successful, else false
     */
    @Override
    public boolean send(Message email) {
        LOGGER.info("Sending email {} ", email);
        try {
            buildSession();
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(isNotBlank(email.getFrom()) ? new InternetAddress(email.getFrom()) : null);
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(
                join(email.getRecipients(), COMMA)));
            message.setSubject(isNotBlank(email.getSubject()) ? email.getSubject() : null);
            message.setText(isNotBlank(email.getText()) ? email.getText() : null);
            Transport.send(message);
            LOGGER.info("Email sent successfully");
        } catch (Exception e) {
            LOGGER.error("An error occurred while sending email", e);
            return false;
        }
        return true;
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
