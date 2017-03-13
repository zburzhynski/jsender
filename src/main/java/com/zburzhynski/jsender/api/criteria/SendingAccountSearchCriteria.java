package com.zburzhynski.jsender.api.criteria;

import com.zburzhynski.jsender.api.domain.SendingType;

import java.util.Set;

/**
 * Sending account search criteria.
 * <p/>
 * Date: 07.03.2017
 *
 * @author Nikita Shevtsou
 */
public class SendingAccountSearchCriteria {

    private Set<SendingType> sendingTypes;

    public Set<SendingType> getSendingTypes() {
        return sendingTypes;
    }

    public void setSendingTypes(Set<SendingType> sendingTypes) {
        this.sendingTypes = sendingTypes;
    }

}
