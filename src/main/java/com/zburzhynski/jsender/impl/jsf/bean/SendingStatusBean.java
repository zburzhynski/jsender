package com.zburzhynski.jsender.impl.jsf.bean;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jsender.api.domain.Params;
import com.zburzhynski.jsender.api.domain.ResponseStatus;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.domain.SendingAccountParam;
import com.zburzhynski.jsender.impl.rest.client.UnisenderRestClient;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsResponse;
import com.zburzhynski.jsender.impl.util.PropertyReader;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Sending status bean.
 * <p/>
 * Date: 10.04.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@SessionScoped
public class SendingStatusBean implements Serializable {

    private static final int SECOND_SCALE = 1000;

    private Message messageToSend;

    private SendingStatus sendingStatus;

    private List<SendingStatus> sendingStatuses;

    @ManagedProperty(value = "#{sendingAccountService}")
    private ISendingAccountService accountService;

    @ManagedProperty(value = "#{unisenderRestClient}")
    private UnisenderRestClient unisenderRestClient;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader reader;

    /**
     * Refreshes sending statues.
     */
    public void refreshSendingStatuses() {
        try {
            if (SendingType.SMS.equals(messageToSend.getSendingType())) {
                SendingAccount account = (SendingAccount) accountService.getById(messageToSend.getSendingAccountId());
                String token = getParamValue(account, Params.TOKEN);
                if (StringUtils.isBlank(token)) {
                    throw new IllegalArgumentException("Token is null");
                }
                for (SendingStatus status : sendingStatuses) {
                    if (ResponseStatus.SENT.equals(status.getStatus())) {
                        CheckSmsRequest request = new CheckSmsRequest();
                        request.setSmsId(Integer.valueOf(status.getId()));
                        request.setToken(token);
                        CheckSmsResponse smsResponse = unisenderRestClient.checkSms(request);
                        if (!new Long(0).equals(smsResponse.getDelivered())) {
                            status.setDeliveryDate(new Date(smsResponse.getDelivered() * SECOND_SCALE));
                            status.setStatus(ResponseStatus.DELIVERED);
                        }
                    }
                }
            }
        } catch (Exception e) {
            addMessage("sendingStatus.refreshStatusError");
        }
    }

    public Message getMessageToSend() {
        return messageToSend;
    }

    public void setMessageToSend(Message messageToSend) {
        this.messageToSend = messageToSend;
    }

    public SendingStatus getSendingStatus() {
        return sendingStatus;
    }

    public void setSendingStatus(SendingStatus sendingStatus) {
        this.sendingStatus = sendingStatus;
    }

    public List<SendingStatus> getSendingStatuses() {
        return sendingStatuses;
    }

    public void setSendingStatuses(List<SendingStatus> sendingStatuses) {
        this.sendingStatuses = sendingStatuses;
    }

    public void setAccountService(ISendingAccountService accountService) {
        this.accountService = accountService;
    }

    public void setUnisenderRestClient(UnisenderRestClient unisenderRestClient) {
        this.unisenderRestClient = unisenderRestClient;
    }

    public void setReader(PropertyReader reader) {
        this.reader = reader;
    }

    /**
     * Adds localisation message to context.
     *
     * @param message localisation message
     */
    private void addMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(reader.readProperty(message), StringUtils.EMPTY);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.addMessage(null, facesMessage);
    }

    private String getParamValue(SendingAccount account, Params param) {
        for (SendingAccountParam accountParam : account.getAccountParams()) {
            if (param.name().equals(accountParam.getParam().getName().toUpperCase())) {
                return accountParam.getValue();
            }
        }
        return null;
    }

}
