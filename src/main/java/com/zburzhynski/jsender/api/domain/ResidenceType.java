package com.zburzhynski.jsender.api.domain;

/**
 * Residence type of patient.
 * <p/>
 * Date: 03.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum ResidenceType {

    C("residenceType.city"),
    R("residenceType.rural");

    private ResidenceType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
