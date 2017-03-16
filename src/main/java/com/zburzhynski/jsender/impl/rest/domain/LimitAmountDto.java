package com.zburzhynski.jsender.impl.rest.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * LimitAmountDto.
 * <p/>
 * Date: 3/16/2017
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class LimitAmountDto implements Serializable {

    private String limit;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

}
