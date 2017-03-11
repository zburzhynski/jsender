package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.SendingType;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Sending type list bean.
 * <p/>
 * Date: 12.02.2017
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@ViewScoped
public class SendingTypeListBean implements Serializable {

    /**
     * Gets sending types.
     *
     * @return sending types
     */
    public Set<SendingType> getSendingTypes() {
        return EnumSet.of(SendingType.EMAIL, SendingType.SMS);
    }

}
