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
public class SMSSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSSender.class);

    private static final String PROTOCOL = "http";

    private static final Integer PORT = 80;

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
            try {
                URL url = new URL(PROTOCOL, "api.smsfeedba" +
                    "ck.ru", PORT, "/messages/v2/send/?phone=%2B" +
                    recipient + "&text=" + URLEncoder.encode(sms.getText(), "UTF-8")
                    + "&sender=" + sms.getFrom());
                URLConnection urlConnection = url.openConnection();
                urlConnection.setRequestProperty("Authorization", authString);
            } catch (Exception ex) {
                response.put(recipient, ex.getMessage());
                LOGGER.error("Error sending sms", ex);
            }
            SentMessage sentMessage = new SentMessage();
            sentMessage.setSentDate(new Date());
            sentMessage.setRecipientId(recipient.getClientId());
            sentMessage.setContactInfo(recipient.getContactInfo());
            sentMessage.setSubject(sms.getSubject());
            sentMessage.setText(sms.getText());
            sentMessage.setStatus(response.get(recipient));
            sentMessage.setType(SendingType.SMS);
            sentMessageRepository.saveOrUpdate(sentMessage);
        }
        return response;
    }


}