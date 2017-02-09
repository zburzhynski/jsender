package com.zburzhynski.jsender.impl.service;

import static com.zburzhynski.jsender.api.domain.CommonConstant.COMMA;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.join;
import com.zburzhynski.jsender.api.dto.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import javax.mail.Message;
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
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private Session session;

    /**
     * Constructor.
     *
     * @param smtpHost smtp host
     * @param smtpPort smtp port
     * @param userName user name
     * @param password user password
     */
    public EmailSender(String smtpHost, String smtpPort, String userName, String password) {
        buildSession(smtpHost, smtpPort, userName, password);
    }

    /**
     * Send email.
     *
     * @param email email to send
     * @throws MessagingException if any
     */
    public void send(Email email) throws MessagingException {
        LOGGER.info("Sending email {} ", email);
        Message message = new MimeMessage(session);
        message.setFrom(isNotBlank(email.getFrom()) ? new InternetAddress(email.getFrom()) : null);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(join(email.getRecipients(), COMMA)));
        message.setSubject(isNotBlank(email.getSubject()) ? email.getSubject() : null);
        message.setText(isNotBlank(email.getText()) ? email.getText() : null);
        Transport.send(message);
        LOGGER.info("Email sent successfully");
    }

    private void buildSession(String smtpHost, String smtpPort, final String userName, final String password) {
        if (isBlank(smtpHost) || isBlank(smtpPort) || isBlank(userName) || isBlank(password)) {
            throw new IllegalArgumentException();
        }
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
