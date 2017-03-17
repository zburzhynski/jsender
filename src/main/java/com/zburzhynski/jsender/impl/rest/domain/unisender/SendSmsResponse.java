package com.zburzhynski.jsender.impl.rest.domain.unisender;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Send sms response.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
@XmlRootElement
public class SendSmsResponse implements Serializable {

    private Integer smsId;

    private String status;

    public Integer getSmsId() {
        return smsId;
    }

    public void setSmsId(Integer smsId) {
        this.smsId = smsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
