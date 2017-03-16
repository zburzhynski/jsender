package com.zburzhynski.jsender.impl.rest.domain.unisender;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Error response.
 * <p/>
 * Date: 16.03.2017
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class ErrorResponse implements Serializable {

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
