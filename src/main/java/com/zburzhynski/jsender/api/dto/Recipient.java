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

    private String contactInfo;

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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

}
