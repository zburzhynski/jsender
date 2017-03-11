package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.service.ISendingServiceService;
import com.zburzhynski.jsender.impl.domain.SendingService;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Sending service list bean.
 * <p/>
 * Date: 07.03.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@ViewScoped
public class SendingServiceListBean implements Serializable {

    private List<SendingService> sendingServices;

    @ManagedProperty(value = "#{sendingServiceService}")
    private ISendingServiceService sendingServiceService;

    /**
     * Inits state bean.
     */
    @PostConstruct
    public void init() {
        sendingServices = sendingServiceService.getAll();
    }

    /**
     * Gets sending services.
     *
     * @return sending services
     */
    public List<SendingService> getSendingServices() {
        return sendingServices;
    }

    public void setSendingServiceService(ISendingServiceService sendingServiceService) {
        this.sendingServiceService = sendingServiceService;
    }

}
