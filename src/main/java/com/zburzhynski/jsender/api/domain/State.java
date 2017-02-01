package com.zburzhynski.jsender.api.domain;

/**
 * The record state.
 * <p/>
 * Date: 02.12.12
 *
 * @author Vladimir Zburzhynski
 */
public enum State {

    ADDED("Added"),
    MODIFIED("Modified"),
    DELETED("Deleted");

    private String value;

    private State(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
