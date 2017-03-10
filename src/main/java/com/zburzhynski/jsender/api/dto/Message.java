package com.zburzhynski.jsender.api.dto;

import com.zburzhynski.jsender.api.domain.SendingType;

import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Message.
 * <p/>
 * Date: 03.08.2016
 *
 * @author Vladimir Zburzhynski
 */
public class Message implements Serializable {

    private String sendingAccountId;

    private SendingType sendingType;

    private String from;

    private List<Recipient> recipients;

    private String subject;

    private String text;

    /**
     * Adds recipient.
     *
     * @param recipient recipient to add
     */
    public void addRecipient(Recipient recipient) {
        for (Recipient client : getRecipients()) {
            if (client.getId().equals(recipient.getId())) {
                return;
            }
        }
        if (CollectionUtils.isNotEmpty(recipient.getPhones()) || CollectionUtils.isNotEmpty(recipient.getEmails())) {
            getRecipients().add(recipient);
        }
    }

    /**
     * Removes recipient.
     *
     * @param recipient recipient to remove
     */
    public void removeRecipient(Recipient recipient) {
        getRecipients().remove(recipient);
    }

    public String getSendingAccountId() {
        return sendingAccountId;
    }

    public void setSendingAccountId(String sendingAccountId) {
        this.sendingAccountId = sendingAccountId;
    }

    public SendingType getSendingType() {
        return sendingType;
    }

    public void setSendingType(SendingType sendingType) {
        this.sendingType = sendingType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets recipients.
     *
     * @return recipients
     */
    public List<Recipient> getRecipients() {
        if (recipients == null) {
            recipients = new ArrayList<>();
        }
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
