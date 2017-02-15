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

    @Column(name = "sent_date")
    private Date sentDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Client.class)
    @JoinColumn(name = "recipient_id")
    private Client recipient;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

    @Enumerated(value = EnumType.STRING)
    private SendingType type;

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Client getRecipient() {
        return recipient;
    }

    public void setRecipient(Client recipient) {
        this.recipient = recipient;
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

    public SendingType getType() {
        return type;
    }

    public void setType(SendingType type) {
        this.type = type;
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
            .append(contactInfo, that.contactInfo)
            .append(subject, that.subject)
            .append(text, that.text)
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
            .append(contactInfo)
            .append(subject)
            .append(text)
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
            .append("contactInfo", contactInfo)
            .append("subject", subject)
            .append("text", text)
            .toString();
    }

}
