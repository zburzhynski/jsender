package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.RecipientSourceType;
import com.zburzhynski.jsender.api.domain.SendingType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Column(name = "sending_type")
    @Enumerated(value = EnumType.STRING)
    private SendingType sendingType;

    @Column(name = "recipient_id")
    private String recipientId;

    @Column(name = "recipient_source")
    @Enumerated(value = EnumType.STRING)
    private RecipientSourceType recipientSource;

    @Column(name = "recipient_fullname")
    private String recipientFullName;

    @Column(name = "contact_info")
    private String contactInfo;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

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

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public RecipientSourceType getRecipientSource() {
        return recipientSource;
    }

    public void setRecipientSource(RecipientSourceType recipientSource) {
        this.recipientSource = recipientSource;
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
            .append(recipientId, that.recipientId)
            .append(recipientSource, that.recipientSource)
            .append(recipientFullName, that.recipientFullName)
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
            .append(sendingType)
            .append(recipientId)
            .append(recipientSource)
            .append(recipientFullName)
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
            .append("sendingType", sendingType)
            .append("recipientId", recipientId)
            .append("recipientSource", recipientSource)
            .append("recipientFullName", recipientFullName)
            .append("contactInfo", contactInfo)
            .append("subject", subject)
            .append("text", text)
            .toString();
    }

}
