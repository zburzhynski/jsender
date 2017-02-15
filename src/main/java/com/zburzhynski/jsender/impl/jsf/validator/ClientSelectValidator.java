package com.zburzhynski.jsender.impl.jsf.validator;

import com.zburzhynski.jsender.impl.domain.Client;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Client select validator.
 * <p/>
 * Date: 12.02.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class ClientSelectValidator extends BaseValidator {

    private static final String CLIENT_NOT_SELECTED = "clientSelectValidator.clientNotSelected";

    /**
     * Validates selected clients.
     *
     * @param selectedClients selected clients to validates
     * @return false if selected clients not valid, else true
     */
    public boolean validate(List<Client> selectedClients) {
        if (CollectionUtils.isEmpty(selectedClients)) {
            addMessage(CLIENT_NOT_SELECTED);
            return false;
        }
        return true;
    }
}
