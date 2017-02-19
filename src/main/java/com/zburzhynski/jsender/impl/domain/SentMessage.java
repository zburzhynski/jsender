package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.SendingType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Sent message.
 * <p/>
 * Date: 16.02.2017
 *
 * @author Nikita Shevtsov
 */
@Entity
@Table(name = "sent_message")
public class SentMessage extends Domain {

    public static final String P_CLIENT = "client";

    @Column(name = "sent_date")
    private Date sentDate = new Date();

    @Column(name = "sending_type")
    @Enumerated(value = EnumType.STRING)
    private SendingType sendingType;

    @Column(name = "client_id")
    private String clientId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Client.class)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

    @Column(name = "status")
    private String status;

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public SendingType getSendingType() {
        return sendingType;
    }

    public void setSendingType(SendingType sendingType) {
        this.sendingType = sendingType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SentMessage)) {
            return false;
        }

        SentMessage that = (SentMessage) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(sentDate, that.sentDate)
            .append(sendingType, that.sendingType)
            .append(clientId, that.clientId)
            .append(contactInfo, that.contactInfo)
            .append(subject, that.subject)
            .append(text, that.text)
            .append(status, that.status)
            .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(sentDate)
            .append(sendingType)
            .append(clientId)
            .append(contactInfo)
            .append(subject)
            .append(text)
            .append(status)
            .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("sentDate", sentDate)
            .append("sendingType", sendingType)
            .append("clientId", clientId)
            .append("contactInfo", contactInfo)
            .append("subject", subject)
            .append("text", text)
            .append("status", status)
            .toString();
    }

}
