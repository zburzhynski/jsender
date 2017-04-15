package com.zburzhynski.jsender.api.domain;

/**
 * Represents sending status.
 * <p/>
 * Date: 21.03.2017
 *
 * @author Nikita Shevtsou
 */
public enum SendingStatus {

    SENT("sendingStatus.sent"),
    DELIVERED("sendingStatus.delivered"),
    ERROR("sendingStatus.error");

    private String value;

    /**
     * Constructor.
     *
     * @param value localization message
     */
    SendingStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
