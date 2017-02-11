package com.zburzhynski.jsender.impl.jsf.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email validator.
 * <p/>
 * Date: 23.06.2014
 *
 * @author Alexey Pchelkin
 */
@Component
public class EmailValidator extends BaseValidator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
        "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
        "(\\.[A-Za-z]{2,})$";

    private static final String INVALID_EMAIL_ADDRESS_MESSAGE = "emailValidator.invalidAddressMessage";

    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private Matcher matcher;

    /**
     * Validates email address.
     *
     * @param emailAddress address to validates
     * @return false if address not valid, else false
     */
    public boolean validate(String emailAddress) {
        if (StringUtils.isNotBlank(emailAddress)) {
            matcher = pattern.matcher(emailAddress);
            if (!matcher.matches()) {
                addMessage(INVALID_EMAIL_ADDRESS_MESSAGE);
                return false;
            }
        }
        return true;
    }

}