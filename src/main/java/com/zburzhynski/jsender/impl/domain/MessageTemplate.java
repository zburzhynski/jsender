package com.zburzhynski.jsender.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Message template.
 * <p/>
 * Date: 20.02.2017
 *
 * @author Nikita Shevtsou
 */
@Entity
@Table(name = "message_template")
public class MessageTemplate extends Domain {

    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

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

        if (!(o instanceof MessageTemplate)) {
            return false;
        }

        MessageTemplate that = (MessageTemplate) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
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
            .append("subject", subject)
            .append("text", text)
            .toString();
    }

}
