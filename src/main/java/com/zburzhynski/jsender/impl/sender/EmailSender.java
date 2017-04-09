package com.zburzhynski.jsender.impl.sender;

import static javax.mail.Message.RecipientType;
import com.zburzhynski.jsender.api.domain.Params;
import com.zburzhynski.jsender.api.domain.ResponseStatus;
import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.exception.SendingException;
import com.zburzhynski.jsender.api.sender.ISender;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.api.service.ISentMessageService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.domain.SendingAccountParam;
import com.zburzhynski.jsender.impl.domain.SentMessage;
import com.zburzhynski.jsender.impl.service.AbstractSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.mail.Authenticator;
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
@Component
public class EmailSender extends AbstractSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    @Autowired
    private ISentMessageService sentMessageService;

    @Autowired
    private ISendingAccountService accountService;

    @Override
    public List<SendingStatus> send(Message email) throws SendingException {
        List<SendingStatus> response = new ArrayList<>();
        Map<Params, SendingAccountParam> params = getAccountParams(email.getSendingAccountId());
        Session session = buildSession(params);
        for (Recipient recipient : email.getRecipients()) {
            for (String address : recipient.getEmails()) {
                LOGGER.info("Sending email {} ", email);
                SendingStatus status = new SendingStatus();
                status.setRecipientFullName(recipient.getFullName());
                status.setContactInfo(address);
                try {
                    javax.mail.Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(params.get(Params.USER_NAME).getValue(), email.getFrom()));
                    message.setRecipients(RecipientType.TO, InternetAddress.parse(address));
                    message.setSubject(StringUtils.isNotBlank(email.getSubject()) ? email.getSubject() : null);
                    message.setContent(StringUtils.isNotBlank(email.getText()) ?
                        prepareText(email.getText(), recipient) : null, CONTENT_TYPE);
                    Transport.send(message);
                    SentMessage sentMessage = new SentMessage();
                    sentMessage.setSentDate(new Date());
                    sentMessage.setRecipientId(recipient.getId());
                    sentMessage.setRecipientSource(recipient.getRecipientSource());
                    sentMessage.setRecipientFullName(recipient.getFullName());
                    sentMessage.setContactInfo(address);
                    sentMessage.setSubject(email.getSubject());
                    sentMessage.setText(email.getText());
                    sentMessage.setSendingType(SendingType.EMAIL);
                    sentMessageService.saveOrUpdate(sentMessage);
                    status.setStatus(ResponseStatus.OK);
                    LOGGER.info("Email sent successfully, address = " + address);
                } catch (Exception e) {
                    status.setStatus(ResponseStatus.ERROR);
                    status.setDescription(e.getClass().getName());
                    LOGGER.error("An error occurred while sending email", e);
                }
                response.add(status);
            }
        }
        return response;
    }

    @Override
    public Set<SendingServices> getSendingServices() {
        return EnumSet.of(SendingServices.GMAIL_COM, SendingServices.YANDEX_RU, SendingServices.MAIL_RU);
    }

    @Override
    public SendingType getSendingType() {
        return SendingType.EMAIL;
    }

    private Session buildSession(Map<Params, SendingAccountParam> params) {
        String smtpHost = params.get(Params.MAIL_SMTP_HOST).getValue();
        String smtpPort = params.get(Params.MAIL_SMTP_PORT).getValue();
        final String userName = params.get(Params.USER_NAME).getValue();
        final String password = params.get(Params.USER_PASSWORD).getValue();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.socketFactory.port", smtpPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
    }

    private Map<Params, SendingAccountParam> getAccountParams(String accountId) {
        Map<Params, SendingAccountParam> params = new HashMap<>();
        SendingAccount account = (SendingAccount) accountService.getById(accountId);
        for (SendingAccountParam param : account.getAccountParams()) {
            params.put(Params.valueOf(param.getParam().getName().toUpperCase()), param);
        }
        return params;
    }

}
