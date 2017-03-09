package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.impl.domain.EmployeeSendingService;
import com.zburzhynski.jsender.impl.domain.EmployeeSendingServiceParam;
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
     * @param account {@link EmployeeSendingService} to validates
     * @return true if account valid, else false
     */
    public boolean validate(EmployeeSendingService account) {
        if (account.getSendingService() == null) {
            addMessage(SENDING_SERVICE_NOT_SPECIFIED);
            return false;
        }
        for (EmployeeSendingServiceParam param : account.getServiceParams()) {
            if (StringUtils.isBlank(param.getValue())) {
                addMessage(PARAM_VALUE_NOT_SPECIFIED);
                return false;
            }
        }
        return true;
    }

}
