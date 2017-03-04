package com.zburzhynski.jsender.api.dto;

/**
 * Sending status.
 * <p/>
 * Date: 03.03.2017
 *
 * @author Nikita Shevtsou
 */
public class SendingStatus {

    private String recipientFullName;

    private String contactInfo;

    private String description;

    public String getRecipientFullName() {
        return recipientFullName;
    }

    public void setRecipientFullName(String recipientFullName) {
        this.recipientFullName = recipientFullName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
