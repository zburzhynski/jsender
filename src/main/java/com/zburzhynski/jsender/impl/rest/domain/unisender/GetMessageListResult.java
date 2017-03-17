package com.zburzhynski.jsender.impl.rest.domain.unisender;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Get message list result.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
@XmlRootElement
public class GetMessageListResult implements Serializable {

    private Integer messageId;

    private String message;

    private Integer parts;

    private Date d_create;

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

    public Date getD_create() {
        return d_create;
    }

    public void setD_create(Date d_create) {
        this.d_create = d_create;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
