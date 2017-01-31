package com.zburzhynski.jsender.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Email.
 * <p/>
 * Date: 03.08.2016
 *
 * @author Vladimir Zburzhynski
 */
public class Email implements Serializable {

    private String from;

    private List<String> recipients;

    private String subject;

    private String text;

    /**
     * Adds recipient.
     *
     * @param recipient recipient to add
     */
    public void addRecipient(String recipient) {
        getRecipients().add(recipient);
    }

    /**
     * Removes recipient.
     *
     * @param recipient recipient to remove
     */
    public void removeRecipient(String recipient) {
        getRecipients().remove(recipient);
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
    public List<String> getRecipients() {
        if (recipients == null) {
            recipients = new ArrayList<>();
        }
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
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
