package com.zburzhynski.jsender.impl.util;

import static com.zburzhynski.jsender.api.domain.CommonConstant.SPACE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.domain.TemplateTag;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Message text helper.
 * <p/>
 * Date: 12.04.2017
 *
 * @author Nikita Shevtsou
 */
@Component
public class TextHelper {

    private static final String HTML_SPACE = "&nbsp;";

    private static final String HTML_TAG = "\\<.*?>";

    @Autowired
    private PropertyReader propertyReader;

    @Autowired
    private ISettingService<String, Setting> settingService;

    /**
     * Prepares sms text.
     *
     * @param text      text to prepare
     * @param recipient recipient
     * @return prepared text
     */
    public String prepareSmsText(String text, Recipient recipient) {
        return htmlToString(prepareText(text, recipient));
    }

    /**
     * Prepares sms text.
     *
     * @param text      text to prepare
     * @param recipient recipient
     * @return prepared text
     */
    public String prepareEmailText(String text, Recipient recipient) {
        return prepareText(text, recipient);
    }

    /**
     * Prepares message text.
     *
     * @param text      text to prepare
     * @param recipient recipient
     * @return parepared text
     */
    private String prepareText(String text, Recipient recipient) {
        text = replaceTag(TemplateTag.CLIENT_SURNAME, text, recipient.getSurname());
        text = replaceTag(TemplateTag.CLIENT_NAME, text, recipient.getName());
        text = replaceTag(TemplateTag.CLIENT_PATRONYMIC, text, recipient.getPatronymic());
        text = replaceTag(TemplateTag.CLIENT_FULL_NAME, text, recipient.getFullName());
        String organizationName = settingService.getByName(Settings.ORGANIZATION_NAME).getValue();
        text = replaceTag(TemplateTag.ORGANIZATION_NAME, text, organizationName);
        String organizationMobilePhoneNumber = settingService.getByName(
            Settings.ORGANIZATION_MOBILE_PHONE_NUMBER).getValue();
        text = replaceTag(TemplateTag.ORGANIZATION_MOBILE_PHONE_NUMBER, text, organizationMobilePhoneNumber);
        String organizationAddress = settingService.getByName(Settings.ORGANIZATION_ADDRESS).getValue();
        text = replaceTag(TemplateTag.ORGANIZATION_ADDRESS, text, organizationAddress);
        return text;
    }

    private String replaceTag(TemplateTag tag, String text, String replacement) {
        return StringUtils.replace(text, propertyReader.readProperty(tag.getValue()), replacement);
    }

    private String htmlToString(String text) {
        return text.replaceAll(HTML_SPACE, SPACE).replaceAll(HTML_TAG, EMPTY).replaceAll(" +", SPACE).trim();
    }

}
