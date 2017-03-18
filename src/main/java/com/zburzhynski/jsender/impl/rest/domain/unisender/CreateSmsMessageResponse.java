package com.zburzhynski.jsender.impl.rest.domain.unisender;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Create sms message response.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
@XmlRootElement
public class CreateSmsMessageResponse implements Serializable {

    private String status;

    private Integer parts;

    @XmlElement(name = "len")
    private Integer length;

    private String messageId;

    private String alphaname;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getParts() {
        return parts;
    }

    public void setParts(Integer parts) {
        this.parts = parts;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getAlphaname() {
        return alphaname;
    }

    public void setAlphaname(String alphaname) {
        this.alphaname = alphaname;
    }

}
