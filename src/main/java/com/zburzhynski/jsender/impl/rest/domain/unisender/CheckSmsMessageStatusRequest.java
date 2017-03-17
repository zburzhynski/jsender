package com.zburzhynski.jsender.impl.rest.domain.unisender;

/**
 * Check sms message status request.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
public class CheckSmsMessageStatusRequest extends BaseUnisenderRequest {

    private Integer messageId;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

}
