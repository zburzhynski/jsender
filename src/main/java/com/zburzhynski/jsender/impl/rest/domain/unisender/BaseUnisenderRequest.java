package com.zburzhynski.jsender.impl.rest.domain.unisender;

import java.io.Serializable;

/**
 * Base unisender.by request.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Vladimir Zburzhynski
 */
public class BaseUnisenderRequest implements Serializable {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
