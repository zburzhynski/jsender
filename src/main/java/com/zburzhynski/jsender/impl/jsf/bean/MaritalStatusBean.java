package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.MaritalStatus;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Marital status values.
 * <p/>
 * Date: 03.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class MaritalStatusBean implements Serializable {

    /**
     * Gets marital status values.
     *
     * @return marital status values
     */
    public MaritalStatus[] getSocialStatuses() {
        return MaritalStatus.values();
    }

}
