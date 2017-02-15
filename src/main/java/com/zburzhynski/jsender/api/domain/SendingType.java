package com.zburzhynski.jsender.api.domain;

/**
 * Represents sending type.
 * <p/>
 * Date: 12.02.2017
 *
 * @author Nikita Shevtsov
 */
public enum SendingType {

    SMS("sendingType.sms"),
    EMAIL("sendingType.email");

    private String value;

    private SendingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
