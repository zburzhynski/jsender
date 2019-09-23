package com.zburzhynski.jsender.api.domain;

/**
 * Class represents marital statuses of person.
 * <p/>
 * Date: 03.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum MaritalStatus {

    N("maritalStatus.male.none", "maritalStatus.female.none"),
    M("maritalStatus.male.married", "maritalStatus.female.married"),
    S("maritalStatus.male.single", "maritalStatus.female.single"),
    D("maritalStatus.male.divorced", "maritalStatus.female.divorced"),
    W("maritalStatus.male.widower", "maritalStatus.female.widower");

    private String male;

    private String female;

    /**
     * Constructor.
     *
     * @param male   male status
     * @param female female status
     */
    MaritalStatus(String male, String female) {
        this.male = male;
        this.female = female;
    }

    public String getMale() {
        return male;
    }

    public String getFemale() {
        return female;
    }

}
