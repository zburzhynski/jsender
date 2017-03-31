package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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

    /**
     * Validates sending.
     *
     * @param message message to validate
     * @return false if message not valid, else true
     */
    public boolean validate(Message message) {
        return validateRequiredFields(message) && validateRecipients(message);
    }

    private boolean validateRequiredFields(Message message) {
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
        if (StringUtils.isBlank(message.getFrom())) {
            addMessage(FROM_NOT_SPECIFIED);
            return false;
        }
        if (CollectionUtils.isEmpty(message.getRecipients())) {
            addMessage(RECIPIENTS_NOT_SPECIFIED);
            return false;
        }
        return true;
    }

    private boolean validateRecipients(Message message) {
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

}

