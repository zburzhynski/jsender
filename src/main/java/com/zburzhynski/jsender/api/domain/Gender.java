package com.zburzhynski.jsender.api.domain;

/**
 * Male or Female.
 * <p/>
 * Date: 29.10.12
 *
 * @author Vladimir Zburzhynski
 */
public enum Gender {
    M("gender.male"),
    F("gender.female");

    private String value;

    private Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
