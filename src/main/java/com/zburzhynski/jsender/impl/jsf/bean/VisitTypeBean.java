package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.VisitType;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Visit type bean.
 * <p/>
 * Date: 27.11.12
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@SessionScoped
public class VisitTypeBean implements Serializable {

    /**
     * Gets visit types.
     *
     * @return visit types
     */
    public VisitType[] getVisitTypes() {
        return VisitType.values();
    }

}
