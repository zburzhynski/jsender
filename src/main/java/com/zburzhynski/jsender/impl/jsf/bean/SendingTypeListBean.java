package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.SendingType;

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
public class SendingTypeListBean {


    /**
     * Gets sending types.
     *
     * @return sending types
     */
    public SendingType[] getSendingTypes() {
        return SendingType.values();
    }

}
