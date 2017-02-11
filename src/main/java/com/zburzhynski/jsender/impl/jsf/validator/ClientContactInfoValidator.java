package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.impl.domain.ContactInfo;
import com.zburzhynski.jsender.impl.domain.ContactInfoEmail;
import com.zburzhynski.jsender.impl.domain.ContactInfoPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Client contact info validator.
 * <p/>
 * Date: 10.02.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class ClientContactInfoValidator extends BaseValidator {

    private static final String PHONE_EXISTS = "clientContactInfoValidator.phoneExists";

    private static final String EMAIL_EXISTS = "clientContactInfoValidator.emailExists";

    @Autowired
    private EmailValidator emailValidator;

    /**
     * Validates contact info phone.
     *
     * @param contactInfo contact info to validates
     * @param phone       phone to validates
     * @return false if phone number not valid, else true
     */
    public boolean validatePhone(ContactInfo contactInfo, ContactInfoPhone phone) {
        for (ContactInfoPhone contactInfoPhone : contactInfo.getPhones()) {
            if (!contactInfoPhone.getUid().equals(phone.getUid())) {
                if (contactInfoPhone.getFullNumber().equalsIgnoreCase(phone.getFullNumber())) {
                    addMessage(PHONE_EXISTS);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Validates contact info email.
     *
     * @param contactInfo contact info to validates
     * @param email       email to validates
     * @return false if email address not valid, else true
     */
    public boolean validateEmail(ContactInfo contactInfo, ContactInfoEmail email) {
        boolean valid = emailValidator.validate(email.getAddress());
        if (!valid) {
            return false;
        }
        for (ContactInfoEmail contactInfoEmail : contactInfo.getEmails()) {
            if (!contactInfoEmail.getUid().equals(email.getUid())) {
                if (contactInfoEmail.getAddress().equalsIgnoreCase(email.getAddress())) {
                    addMessage(EMAIL_EXISTS);
                    return false;
                }
            }
        }
        return true;
    }

}
