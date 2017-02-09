package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.Gender;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Gender bean represents male or female.
 * <p/>
 * Date: 01.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class GenderBean implements Serializable {

    /**
     * Gets gender values.
     *
     * @return gender values
     */
    public Gender[] getGenders() {
        return Gender.values();
    }

}
