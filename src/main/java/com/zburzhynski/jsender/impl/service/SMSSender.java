package com.zburzhynski.jsender.impl.service;

import static com.zburzhynski.jsender.api.domain.CommonConstant.COLON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class SMSSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSSender.class);

    private static final String PROTOCOL = "http";

    private static final Integer PORT = 80;

    private static void sendSms(String phone, String text, String sender, String name, String password) {
        try {
            String authString = name + COLON + password;
            String authStringEnc = null;

            URL url = new URL(PROTOCOL, "api.smsfeedback.ru", PORT, "/messages/v2/send/?phone=%2B" +
                phone + "&text=" + URLEncoder.encode(text, "UTF-8") + "&sender=" + sender);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", authStringEnc);
        } catch (Exception ex) {
            LOGGER.error("Error sending sms", ex);
        }
    }


}