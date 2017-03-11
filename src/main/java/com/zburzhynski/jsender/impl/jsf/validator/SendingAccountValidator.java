package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.domain.SendingAccountParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Sending account validator.
 * <p/>
 * Date: 09.03.2017
 *
 * @author Nikita Shevtsou
 */
@Component
public class SendingAccountValidator extends BaseValidator {

    private static final String SENDING_SERVICE_NOT_SPECIFIED = "sendingAccountValidator.sendingServiceNotSpecified";

    private static final String PARAM_VALUE_NOT_SPECIFIED = "sendingAccountValidator.paramValueNotSpecified";

    /**
     * Validates sending service account.
     *
     * @param account {@link SendingAccount} to validates
     * @return true if account valid, else false
     */
    public boolean validate(SendingAccount account) {
        if (account.getSendingService() == null) {
            addMessage(SENDING_SERVICE_NOT_SPECIFIED);
            return false;
        }
        for (SendingAccountParam param : account.getAccountParams()) {
            if (StringUtils.isBlank(param.getValue())) {
                addMessage(PARAM_VALUE_NOT_SPECIFIED, param.getParam().getDescription());
                return false;
            }
        }
        return true;
    }

}
