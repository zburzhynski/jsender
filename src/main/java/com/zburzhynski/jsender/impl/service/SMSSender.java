package com.zburzhynski.jsender.impl.service;

import static com.zburzhynski.jsender.api.domain.CommonConstant.COLON;
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

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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

    @Autowired
    private ISettingRepository<String, Setting> settingRepository;

    /**
     * Send sms.
     *
     * @param sms sms to send
     * @return true if sending is successful, else false
     */
    @Override
    public boolean send(Message sms) {
        try {
            String name = settingRepository.findByName(Settings.SMS_USER_NAME).getValue();
            String password = settingRepository.findByName(Settings.SMS_PASSWORD).getValue();
            String authString = name + COLON + password;
            for (String recipient : sms.getRecipients()) {
                URL url = new URL(PROTOCOL, "api.smsfeedback.ru", PORT, "/messages/v2/send/?phone=%2B" +
                    recipient + "&text=" + URLEncoder.encode(sms.getText(), "UTF-8")
                    + "&sender=" + sms.getFrom());
                URLConnection urlConnection = url.openConnection();
                urlConnection.setRequestProperty("Authorization", authString);
            }
        } catch (Exception ex) {
            LOGGER.error("Error sending sms", ex);
            return false;
        }
        return true;
    }


}