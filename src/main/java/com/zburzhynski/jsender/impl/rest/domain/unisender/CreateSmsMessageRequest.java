package com.zburzhynski.jsender.impl.rest.domain.unisender;

/**
 * Create sms message request.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
public class CreateSmsMessageRequest extends BaseUnisenderRequest {

    private String message;

    private Integer alphanameId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAlphanameId() {
        return alphanameId;
    }

    public void setAlphanameId(Integer alphanameId) {
        this.alphanameId = alphanameId;
    }

}
