package com.zburzhynski.jsender.impl.rest.domain.unisender;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Get message list response.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Nikita Shevtsou
 */
@XmlRootElement
public class GetMessageListResponse implements Serializable {

    private List<GetMessageListResult> result;

    public List<GetMessageListResult> getResult() {
        return result;
    }

    public void setResult(List<GetMessageListResult> result) {
        this.result = result;
    }

}
