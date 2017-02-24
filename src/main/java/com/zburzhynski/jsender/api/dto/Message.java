package com.zburzhynski.jsender.api.dto;

import java.io.Serializable;

/**
 * Message.
 * <p/>
 * Date: 03.08.2016
 *
 * @author Vladimir Zburzhynski
 */
public class Message implements Serializable {

    private String from;

    private Recipient recipient;

    private String subject;

    private String text;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
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
