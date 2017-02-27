package com.zburzhynski.jsender.impl.rest.domain;

import java.io.Serializable;

/**
 * Base dto.
 * <p/>
 * Date: 26.02.2017
 *
 * @author Vladimir Zburzhynski
 */
public class BaseDto implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
