package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.domain.TemplateTag;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.repository.ISettingRepository;
import com.zburzhynski.jsender.impl.domain.Setting;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract sender.
 * <p/>
 * Date: 01.03.2017
 *
 * @author Nikita Shevtsou
 */
public abstract class AbstractSender {

    @Autowired
    private PropertyReader propertyReader;

    @Autowired
    private ISettingRepository<String, Setting> settingRepository;

    /**
     * Prepares message text.
     *
     * @param text text to prepare
     * @param recipient recipient
     *
     * @return parepared text
     */
    public String prepareText(String text, Recipient recipient) {
        text = replaceTag(TemplateTag.CLIENT_SURNAME, text, recipient.getSurname());
        text = replaceTag(TemplateTag.CLIENT_NAME, text, recipient.getName());
        text = replaceTag(TemplateTag.CLIENT_PATRONYMIC, text, recipient.getPatronymic());
        text = replaceTag(TemplateTag.CLIENT_FULL_NAME, text, recipient.getFullName());
        String organizationName = settingRepository.findByName(Settings.ORGANIZATION_NAME).getValue();
        text = replaceTag(TemplateTag.ORGANIZATION_NAME, text, organizationName);
        String organizationMobilePhoneNumber = settingRepository.findByName(
            Settings.ORGANIZATION_MOBILE_PHONE_NUMBER).getValue();
        text = replaceTag(TemplateTag.ORGANIZATION_MOBILE_PHONE_NUMBER, text, organizationMobilePhoneNumber);
        String organizationAddress = settingRepository.findByName(Settings.ORGANIZATION_ADDRESS).getValue();
        text = replaceTag(TemplateTag.ORGANIZATION_ADDRESS, text, organizationAddress);
        return text;
    }

    private String replaceTag(TemplateTag tag, String text, String replacement) {
        return StringUtils.replace(text, propertyReader.readProperty(tag.getValue()), replacement);
    }

}
