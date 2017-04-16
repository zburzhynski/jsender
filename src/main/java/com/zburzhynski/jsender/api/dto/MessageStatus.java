package com.zburzhynski.jsender.api.dto;

import com.zburzhynski.jsender.api.domain.SendingStatus;

import java.util.Date;

/**
 * Message status.
 * <p/>
 * Date: 03.03.2017
 *
 * @author Nikita Shevtsou
 */
public class MessageStatus {

    private String id;

    private String recipientFullName;

    private String contactInfo;

    private String text;

    private Integer parts;

    private Date sendingDate;

    private Date deliveryDate;

    private SendingStatus status;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getParts() {
        return parts;
    }

    public void setParts(Integer parts) {
        this.parts = parts;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public SendingStatus getStatus() {
        return status;
    }

    public void setStatus(SendingStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
