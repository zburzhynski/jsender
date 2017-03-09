package com.zburzhynski.jsender.api.criteria;

import com.zburzhynski.jsender.api.domain.SendingType;

/**
 * Employee sending service search criteria.
 * <p/>
 * Date: 07.03.2017
 *
 * @author Nikita Shevtsou
 */
public class EmployeeSendingServiceSearchCriteria {

    private SendingType sendingType;

    public SendingType getSendingType() {
        return sendingType;
    }

    public void setSendingType(SendingType sendingType) {
        this.sendingType = sendingType;
    }

}
