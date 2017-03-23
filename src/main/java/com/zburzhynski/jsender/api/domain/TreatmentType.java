package com.zburzhynski.jsender.api.domain;

/**
 * Represents treatment types.
 * <p/>
 * Date: 01.12.13
 *
 * @author Vladimir Zburzhynski
 */
public enum TreatmentType {

    THERAPEUTIC("treatmentType.therapeutic"),
    ORTHOPEDIC("treatmentType.orthopedic"),
    SURGICAL("treatmentType.surgical"),
    ORTHODONTIC("treatmentType.orthodontic");

    private String value;

    private TreatmentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
