package com.zburzhynski.jsender.api.domain;

/**
 * Represents types of dental visit.
 * <p/>
 * Date: 10.11.12
 *
 * @author Vladimir Zburzhynski
 */
public enum VisitType {

    P("visitType.primary", "I"),
    R("visitType.repeat", "II");

    private String value;
    private String title;

    private VisitType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

}
