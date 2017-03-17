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

    private String alphanameId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlphanameId() {
        return alphanameId;
    }

    public void setAlphanameId(String alphanameId) {
        this.alphanameId = alphanameId;
    }

}
