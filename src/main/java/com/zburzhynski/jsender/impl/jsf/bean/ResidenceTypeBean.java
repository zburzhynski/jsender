package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.ResidenceType;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Residence type bean.
 * <p/>
 * Date: 03.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class ResidenceTypeBean implements Serializable {

    /**
     * Gets residence type values.
     *
     * @return residence type values
     */
    public ResidenceType[] getResidenceTypes() {
        return ResidenceType.values();
    }

}
