package com.zburzhynski.jsender.impl.jsf.validator;

import static com.zburzhynski.jsender.api.domain.TemplateTag.ORGANIZATION_ADDRESS;
import static com.zburzhynski.jsender.api.domain.TemplateTag.ORGANIZATION_MOBILE_PHONE_NUMBER;
import static com.zburzhynski.jsender.api.domain.TemplateTag.ORGANIZATION_NAME;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.domain.TemplateTag;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sending validator.
 * <p/>
 * Date: 01.03.2017
 *
 * @author Nikita Shevtsou
 */
@Component
public class SendingValidator extends BaseValidator {

    private static final String SENDING_TYPE_NOT_SPECIFIED = "sendingValidator.sendingTypeNotSpecified";

    private static final String SENDING_ACCOUNT_NOT_SPECIFIED = "sendingValidator.sendingAccountNotSpecified";

    private static final String SUBJECT_NOT_SPECIFIED = "sendingValidator.subjectNotSpecified";

    private static final String TEXT_NOT_SPECIFIED = "sendingValidator.textNotSpecified";

    private static final String FROM_NOT_SPECIFIED = "sendingValidator.fromNotSpecified";

    private static final String RECIPIENTS_NOT_SPECIFIED = "sendingValidator.recipientsNotSpecified";

    private static final String MOBILE_PHONE_NUMBER_NOT_SPECIFIED = "sendingValidator.mobilePhoneNumberNotSpecified";

    private static final String EMAIL_NOT_SPECIFIED = "sendingValidator.emailNotSpecified";

    private static final String INVALID_TAG = "sendingValidator.invalidTag";

    private static final String ORGANIZATION_NAME_TAG_NOT_SPECIFIED =
        "sendingValidator.organizationNameTagNotSpecified";

    private static final String ORGANIZATION_ADDRESS_TAG_NOT_SPECIFIED =
        "sendingValidator.organizationAddressTagNotSpecified";

    private static final String ORGANIZATION_MOBILE_PHONE_NUMBER_TAG_NOT_SPECIFIED =
        "sendingValidator.organizationMobilePhoneNumberTagNotSpecified";

    @Autowired
    private ISettingService settingService;

    @Autowired
    private PropertyReader reader;

    /**
     * Validates sending.
     *
     * @param message message to validate
     * @return false if message not valid, else true
     */
    public boolean validate(Message message) {
        return checkRequiredFields(message) && checkRecipients(message) && checkTags(message);
    }

    private boolean checkRequiredFields(Message message) {
        if (message.getSendingType() == null) {
            addMessage(SENDING_TYPE_NOT_SPECIFIED);
            return false;
        }
        if (StringUtils.isBlank(message.getSendingAccountId())) {
            addMessage(SENDING_ACCOUNT_NOT_SPECIFIED);
            return false;
        }
        if (SendingType.EMAIL.equals(message.getSendingType()) && StringUtils.isBlank(message.getSubject())) {
            addMessage(SUBJECT_NOT_SPECIFIED);
            return false;
        }
        if (StringUtils.isBlank(message.getText())) {
            addMessage(TEXT_NOT_SPECIFIED);
            return false;
        }
        if (SendingType.EMAIL.equals(message.getSendingType()) && StringUtils.isBlank(message.getFrom())) {
            addMessage(FROM_NOT_SPECIFIED);
            return false;
        }
        if (CollectionUtils.isEmpty(message.getRecipients())) {
            addMessage(RECIPIENTS_NOT_SPECIFIED);
            return false;
        }
        return true;
    }

    private boolean checkRecipients(Message message) {
        switch (message.getSendingType()) {
            case SMS:
                for (Recipient recipient : message.getRecipients()) {
                    if (CollectionUtils.isNotEmpty(recipient.getPhones())) {
                        return true;
                    }
                }
                addMessage(MOBILE_PHONE_NUMBER_NOT_SPECIFIED);
                return false;
            case EMAIL:
                for (Recipient recipient : message.getRecipients()) {
                    if (CollectionUtils.isNotEmpty(recipient.getEmails())) {
                        return true;
                    }
                }
                addMessage(EMAIL_NOT_SPECIFIED);
                return false;
            default:
        }
        return true;
    }

    private boolean checkTags(Message message) {
        Set<String> tags = new HashSet<>();
        for (TemplateTag templateTag : TemplateTag.values()) {
            tags.add(reader.readProperty(templateTag.getValue()));
        }
        Pattern tagPattern = Pattern.compile("\\{.*?}");
        Matcher matcher = tagPattern.matcher(message.getText());
        while (matcher.find()) {
            String tag = matcher.group();
            if (!tags.contains(tag)) {
                addMessage(INVALID_TAG, tag);
                return false;
            }
        }
        if (message.getText().contains(reader.readProperty(ORGANIZATION_NAME.getValue()))) {
            Setting setting = (Setting) settingService.getByName(Settings.ORGANIZATION_NAME);
            if (StringUtils.isEmpty(setting.getValue())) {
                addMessage(ORGANIZATION_NAME_TAG_NOT_SPECIFIED);
                return false;
            }
        }
        if (message.getText().contains(reader.readProperty(ORGANIZATION_ADDRESS.getValue()))) {
            Setting setting = (Setting) settingService.getByName(Settings.ORGANIZATION_ADDRESS);
            if (StringUtils.isEmpty(setting.getValue())) {
                addMessage(ORGANIZATION_ADDRESS_TAG_NOT_SPECIFIED);
                return false;
            }
        }
        if (message.getText().contains(reader.readProperty(ORGANIZATION_MOBILE_PHONE_NUMBER.getValue()))) {
            Setting setting = (Setting) settingService.getByName(Settings.ORGANIZATION_MOBILE_PHONE_NUMBER);
            if (StringUtils.isEmpty(setting.getValue())) {
                addMessage(ORGANIZATION_MOBILE_PHONE_NUMBER_TAG_NOT_SPECIFIED);
                return false;
            }
        }
        return true;
    }

}