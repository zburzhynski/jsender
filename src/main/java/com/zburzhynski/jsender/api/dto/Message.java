package com.zburzhynski.jsender.api.dto;

import com.zburzhynski.jsender.api.domain.SendingType;

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

    private String from;

    private List<Recipient> recipients = new ArrayList<>();

    private String subject;

    private String text;

    private SendingType sendingType;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<Recipient> getRecipients() {
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

    public SendingType getSendingType() {
        return sendingType;
    }

    public void setSendingType(SendingType sendingType) {
        this.sendingType = sendingType;
    }

}
