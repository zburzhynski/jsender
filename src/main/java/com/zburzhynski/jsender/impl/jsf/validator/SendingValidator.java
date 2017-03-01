package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.impl.domain.Setting;
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

    /**
     * Validates sending.
     *
     * @param setting setting to validate
     * @return false if setting not valid, else true
     */
    public boolean validate(Setting setting) {
//        return checkDateFormat(setting);
        return false;
    }


}
