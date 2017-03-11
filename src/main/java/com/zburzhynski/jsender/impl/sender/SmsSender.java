package com.zburzhynski.jsender.impl.sender;

import static com.zburzhynski.jsender.api.domain.CommonConstant.COLON;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.sender.ISender;
import com.zburzhynski.jsender.api.service.ISentMessageService;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.SentMessage;
import com.zburzhynski.jsender.impl.domain.Setting;
import com.zburzhynski.jsender.impl.service.AbstractSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SMS sender.
 * <p/>
 * Date: 31.01.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class SmsSender extends AbstractSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSender.class);

    private static final String PROTOCOL = "http";

    private static final Integer PORT = 80;

    private static final Integer RESPONSE_ARRAY_SIZE = 1024;

    @Autowired
    private ISentMessageService sentMessageService;

    @Autowired
    private ISettingService settingService;

    /**
     * Send sms.
     *
     * @param message message to send
     * @return sending response
     */
    @Override
    public List<SendingStatus> send(Message message) {
        List<SendingStatus> response = new ArrayList<>();
        String name = ((Setting) settingService.getByName(Settings.SMS_USER_NAME)).getValue();
        String password = ((Setting) settingService.getByName(Settings.SMS_PASSWORD)).getValue();
        String authString = name + COLON + password;
        for (Recipient recipient : message.getRecipients()) {
            for (String phoneNumber : recipient.getPhones()) {
                SendingStatus status = new SendingStatus();
                status.setRecipientFullName(recipient.getFullName());
                status.setContactInfo(phoneNumber);
                try {
                    URL url = new URL(PROTOCOL, "api.smsfeedback.ru", PORT, "/messages/v2/send/?phone=%2B" +
                        phoneNumber + "&text=" + URLEncoder.encode(prepareText(message.getText(), recipient),
                        "UTF-8") + "&sender=" + message.getFrom());
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.setRequestProperty("Authorization", authString);
                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int numCharsRead;
                    char[] charArray = new char[RESPONSE_ARRAY_SIZE];
                    StringBuffer sb = new StringBuffer();
                    while ((numCharsRead = isr.read(charArray)) > 0) {
                        sb.append(charArray, 0, numCharsRead);
                    }
                    status.setDescription("SMS sent successfully");
                } catch (Exception ex) {
                    status.setDescription(ex.getClass().getName());
                    LOGGER.error("Error sending sms", ex);
                }
                SentMessage sentMessage = new SentMessage();
                sentMessage.setSentDate(new Date());
                sentMessage.setRecipientId(recipient.getId());
                sentMessage.setRecipientSource(recipient.getRecipientSource());
                sentMessage.setRecipientFullName(recipient.getFullName());
                sentMessage.setContactInfo(phoneNumber);
                sentMessage.setSubject(message.getSubject());
                sentMessage.setText(message.getText());
                sentMessage.setSendingType(SendingType.SMS);
                sentMessageService.saveOrUpdate(sentMessage);
                response.add(status);
            }
        }
        return response;
    }


}