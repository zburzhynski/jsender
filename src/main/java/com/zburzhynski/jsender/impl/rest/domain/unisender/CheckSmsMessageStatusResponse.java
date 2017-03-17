package com.zburzhynski.jsender.impl.rest.domain.unisender;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Check sms message status response.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
@XmlRootElement
public class CheckSmsMessageStatusResponse implements Serializable {

    private String status;

    private Integer parts;

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

    public String getAlphaname() {
        return alphaname;
    }

    public void setAlphaname(String alphaname) {
        this.alphaname = alphaname;
    }

}
