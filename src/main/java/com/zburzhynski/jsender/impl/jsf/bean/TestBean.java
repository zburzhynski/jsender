package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.impl.rest.client.UnisenderRestClient;
import com.zburzhynski.jsender.impl.rest.domain.unisender.BaseUnisenderRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsMessageStatusRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsMessageStatusResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CreateSmsMessageRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CreateSmsMessageResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.GetLimitRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.GetLimitResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.GetMessageListResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.SendSmsRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.SendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.URLEncoder;
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

    private String token = "b374580e1efce9b53c881487275cd3b0";

    private Integer messageId = 63033;

    private String result;

    @ManagedProperty(value = "#{unisenderRestClient}")
    private UnisenderRestClient unisenderRestClient;

    public void createSmsMessage() {
        try {
            CreateSmsMessageRequest request = new CreateSmsMessageRequest();
            request.setToken(token);
            request.setMessage(URLEncoder.encode("Test message", "UTF-8"));
            request.setAlphanameId("system");
            CreateSmsMessageResponse response = unisenderRestClient.createSmsMessage(request);
            result = response.getStatus();
            LOGGER.info(response.toString());
        } catch (Exception e) {
            LOGGER.warn("Error occurred", e);
        }
    }

    public void checkSmsMessageStatus() {
        try {
            CheckSmsMessageStatusRequest request = new CheckSmsMessageStatusRequest();
            request.setToken(token);
            request.setMessageId(messageId);
            CheckSmsMessageStatusResponse response = unisenderRestClient.checkSmsMessageStatus(request);
            result = response.toString();
            LOGGER.info(response.getStatus());
        } catch (Exception e) {
            LOGGER.warn("Error occurred", e);
        }
    }

    public void getMessagesList() {
        try {
            BaseUnisenderRequest request = new BaseUnisenderRequest();
            request.setToken(token);
            GetMessageListResponse response = unisenderRestClient.getMessageList(request);
            result = response.getResult().toString();
            LOGGER.info(response.toString());
        } catch (Exception e) {
            LOGGER.warn("Error occurred", e);
        }
    }

    public void sendSms() {
        try {
            SendSmsRequest request = new SendSmsRequest();
            request.setToken(token);
            request.setMessageId(messageId);
            request.setPhone("+375299999999");
            SendSmsResponse response = unisenderRestClient.sendSms(request);
            result = response.getStatus();
            LOGGER.info(response.getStatus());
        } catch (Exception e) {
            LOGGER.warn("Error occurred", e);
        }
    }

    public void getLimit() {
        try {
            GetLimitRequest request = new GetLimitRequest();
            request.setToken(token);
            GetLimitResponse response = unisenderRestClient.getLimit(request);
            result = response.getLimit();
            LOGGER.info(response.toString());
        } catch (Exception e) {
            LOGGER.warn("Error occurred", e);
        }
    }

    public void checkSms() {
        try {
            CheckSmsRequest request = new CheckSmsRequest();
            request.setToken(token);
            request.setSmsId(messageId);
            CheckSmsResponse response = unisenderRestClient.checkSms(request);
            result = response.getDelivered().toString();
            LOGGER.info(response.toString());
        } catch (Exception e) {
            LOGGER.warn("Error occurred", e);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setUnisenderRestClient(UnisenderRestClient unisenderRestClient) {
        this.unisenderRestClient = unisenderRestClient;
    }

}
