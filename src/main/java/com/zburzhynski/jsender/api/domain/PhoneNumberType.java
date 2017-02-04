package com.zburzhynski.jsender.api.domain;

/**
 * Represents phone number type.
 * <p/>
 * Date: 23.07.2016
 *
 * @author Nikita Shevtsov
 */
public enum PhoneNumberType {

    MOBILE("phoneNumberType.mobile"),
    LANDLINE("phoneNumberType.landline");


    private String value;

    private PhoneNumberType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
