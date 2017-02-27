package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.SocialStatus;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Social status values.
 * <p/>
 * Date: 03.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class SocialStatusBean implements Serializable {

    /**
     * Gets social status values.
     *
     * @return social status values
     */
    public SocialStatus[] getSocialStatuses() {
        return SocialStatus.values();
    }

}
