package com.zburzhynski.jsender.impl.rest.domain.unisender;

import com.zburzhynski.jsender.impl.rest.domain.unisender.adapter.DateAdapter;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Get message list result.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
@XmlRootElement
public class GetMessageListResult implements Serializable {

    @XmlElement(name = "message_id")
    private Integer messageId;

    private String message;

    private Integer parts;

    @XmlElement(name = "d_create")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date creationDate;

    private String status;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getParts() {
        return parts;
    }

    public void setParts(Integer parts) {
        this.parts = parts;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
