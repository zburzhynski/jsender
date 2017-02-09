package com.zburzhynski.jsender.impl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Localization property reader.
 * <p/>
 * Date: 25.12.12
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class PropertyReader {

    private Locale locale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();

    @Autowired
    private MessageSource messageSource;

    /**
     * Read property from file.
     *
     * @param key property key
     * @return property value
     */
    public String readProperty(String key) {
        return messageSource.getMessage(key, null, locale);
    }

    /**
     * Reads property from file.
     *
     * @param key property key
     * @param params property parameters
     * @return property value
     */
    public String readProperty(String key, Object... params) {
        return messageSource.getMessage(key, params, locale);
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
