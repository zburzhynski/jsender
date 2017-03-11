package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.PhoneNumberType;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Phone number type list bean.
 * <p/>
 * Date: 25.07.2016
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@ViewScoped
public class PhoneNumberTypeListBean implements Serializable {

    /**
     * Gets phone number types.
     *
     * @return phone number types
     */
    public PhoneNumberType[] getPhoneNumberTypes() {
        return PhoneNumberType.values();
    }

}
