package com.zburzhynski.jsender.api.exception;

/**
 * Sending exception.
 * <p/>
 * Date: 20.03.2017
 *
 * @author Nikita Shevtsou
 */
public class SendingException extends Exception {

    private String message;

    /**
     * Constructor.
     *
     * @param message localization message
     */
    public SendingException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
