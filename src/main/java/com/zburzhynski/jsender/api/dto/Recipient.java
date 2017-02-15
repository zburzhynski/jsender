package com.zburzhynski.jsender.api.dto;

/**
 * Recipient.
 * <p/>
 * Date: 16.02.2017
 *
 * @author Nikita Shevtsov
 */
public class Recipient {

    private String clientId;

    private String fullName;

    private String contact;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
