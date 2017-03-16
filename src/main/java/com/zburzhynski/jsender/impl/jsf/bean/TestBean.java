package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.impl.rest.client.UnisenderRestClient;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsResponse;
import com.zburzhynski.jsender.impl.rest.exception.unisender.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Test bean.
 * <p/>
 * Date: 3/16/2017
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TestBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBean.class);

    @ManagedProperty(value = "#{unisenderRestClient}")
    private UnisenderRestClient unisenderRestClient;

    public void getLimit() {
        String token = "b374580e1efce9b53c881487275cd3b0";
        int smsId = 1;
        try {
            CheckSmsResponse response = unisenderRestClient.checkSms(token, smsId);
            LOGGER.info(response.toString());
        } catch (NotFoundException e) {
            LOGGER.warn("", e);
        }
    }

    public void setUnisenderRestClient(UnisenderRestClient unisenderRestClient) {
        this.unisenderRestClient = unisenderRestClient;
    }

}
