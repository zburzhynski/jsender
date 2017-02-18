package com.zburzhynski.jsender.impl.service;

import static com.zburzhynski.jsender.api.domain.CommonConstant.COLON;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * SMS sender.
 * <p/>
 * Date: 31.01.2017
 *
 * @author Nikita Shevtsov
 */
@Service("smsSender")
@Transactional(readOnly = true)
public class SmsSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSender.class);

    private static final String PROTOCOL = "http";

    private static final Integer PORT = 80;

    private static final Integer RESPONSE_ARRAY_SIZE = 1024;

    @Autowired
    private ISentMessageRepository<String, SentMessage> sentMessageRepository;

    @Autowired
    private ISettingRepository<String, Setting> settingRepository;

    /**
     * Send sms.
     *
     * @param sms sms to send
     * @return sending response
     */
    @Override
    public Map<Recipient, String> send(Message sms) {
        Map<Recipient, String> response = new HashMap<>();
        String name = settingRepository.findByName(Settings.SMS_USER_NAME).getValue();
        String password = settingRepository.findByName(Settings.SMS_PASSWORD).getValue();
        String authString = name + COLON + password;
        for (Recipient recipient : sms.getRecipients()) {
            String status;
            try {
                URL url = new URL(PROTOCOL, "api.smsfeedback.ru", PORT, "/messages/v2/send/?phone=%2B" +
                    recipient.getContactInfo() + "&text=" + URLEncoder.encode(sms.getText(), "UTF-8")
                    + "&sender=" + sms.getFrom());
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
                status = "SMS sent successfully";
            } catch (Exception ex) {
                status = ex.getClass().toString();
                LOGGER.error("Error sending sms", ex);
            }
            SentMessage sentMessage = new SentMessage();
            sentMessage.setSentDate(new Date());
            sentMessage.setClientId(recipient.getClientId());
            sentMessage.setContactInfo(recipient.getContactInfo());
            sentMessage.setSubject(sms.getSubject());
            sentMessage.setText(sms.getText());
            sentMessage.setStatus(status);
            sentMessage.setSendingType(SendingType.SMS);
            sentMessageRepository.saveOrUpdate(sentMessage);
            response.put(recipient, status);

        }
        return response;
    }


}