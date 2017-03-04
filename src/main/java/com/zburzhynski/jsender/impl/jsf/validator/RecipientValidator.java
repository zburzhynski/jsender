package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.impl.rest.domain.PatientDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Recipient validator.
 * <p/>
 * Date: 04.03.2017
 *
 * @author Nikita Shevtsou
 */
@Component
public class RecipientValidator extends BaseValidator {

    private static final String RECIPIENT_NOT_SELECTED = "recipientValidator.recipientNotSelected";

    private static final String MOBILE_PHONE_NUMBER_NOT_SPECIFIED = "recipientValidator.mobilePhoneNumberNotSpecified";

    private static final String EMAIL_NOT_SPECIFIED = "recipientValidator.emailNotSpecified";

    /**
     * Validates selected recipient.
     *
     * @param message message to validates
     * @param recipients recipients to validates
     * @return false if selected client not valid, else true
     */
    public boolean validate(Message message, List<PatientDto> recipients) {
        boolean valid = true;
        if (message.getSendingType() == null) {
            return true;
        }
        if (CollectionUtils.isEmpty(recipients)) {
            addMessage(RECIPIENT_NOT_SELECTED);
            return false;
        }
        for (PatientDto recipient : recipients) {
            switch (message.getSendingType()) {
                case SMS:
                    if (CollectionUtils.isEmpty(recipient.getContactInfo().getPhones())) {
                        addMessage(MOBILE_PHONE_NUMBER_NOT_SPECIFIED, recipient.getFullName());
                        valid = false;
                    }
                    break;
                case EMAIL:
                    if (CollectionUtils.isEmpty(recipient.getContactInfo().getEmails())) {
                        addMessage(EMAIL_NOT_SPECIFIED, recipient.getFullName());
                        valid = false;
                    }
                    break;
                    default:
            }
        }
        return valid;
    }

}
