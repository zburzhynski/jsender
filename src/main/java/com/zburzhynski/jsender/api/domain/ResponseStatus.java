package com.zburzhynski.jsender.api.domain;

/**
 * Represents response status.
 * <p/>
 * Date: 21.03.2017
 *
 * @author Nikita Shevtsou
 */
public enum ResponseStatus {

    SENT("responseStatus.sent"),
    DELIVERED("responseStatus.delivered"),
    ERROR("responseStatus.error");

    private String value;

    /**
     * Constructor.
     *
     * @param value localization message
     */
    ResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
