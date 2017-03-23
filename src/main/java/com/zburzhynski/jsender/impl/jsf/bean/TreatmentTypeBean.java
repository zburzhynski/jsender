package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.TreatmentType;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Treatment type bean.
 * <p/>
 * Date: 01.12.13
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TreatmentTypeBean implements Serializable {

    /**
     * Gets visit info types.
     *
     * @return visit info types
     */
    public TreatmentType[] getTreatmentTypes() {
        return TreatmentType.values();
    }

}
