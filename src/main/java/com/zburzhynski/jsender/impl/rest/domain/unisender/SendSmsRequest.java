package com.zburzhynski.jsender.impl.rest.domain.unisender;

/**
 * Send sms request.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
public class SendSmsRequest extends BaseUnisenderRequest {

    private Integer messageId;

    private String phone;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
