package com.zburzhynski.jsender.api.domain;

/**
 * Class represents social statuses of person.
 * <p/>
 * Date: 03.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum SocialStatus {

    N("socialStatus.male.none", "socialStatus.female.none"),
    M("socialStatus.male.married", "socialStatus.female.married"),
    S("socialStatus.male.single", "socialStatus.female.single"),
    D("socialStatus.male.divorced", "socialStatus.female.divorced"),
    W("socialStatus.male.widower", "socialStatus.female.widower");

    private String male;

    private String female;

    /**
     * Constructor.
     *
     * @param male   male status
     * @param female female status
     */
    SocialStatus(String male, String female) {
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
