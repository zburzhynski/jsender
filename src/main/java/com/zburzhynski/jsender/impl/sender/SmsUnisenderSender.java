package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.domain.Params;
import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.sender.ISender;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.api.service.ISentMessageService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.domain.SendingAccountParam;
import com.zburzhynski.jsender.impl.domain.SentMessage;
import com.zburzhynski.jsender.impl.rest.client.UnisenderRestClient;
import com.zburzhynski.jsender.impl.rest.domain.unisender.BaseUnisenderRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsMessageStatusRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsMessageStatusResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CreateSmsMessageRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.GetMessageListResult;
import com.zburzhynski.jsender.impl.rest.domain.unisender.SendSmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Sms unisender.by sender.
 * <p/>
 * Date: 16.03.2017
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class SmsUnisenderSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsUnisenderSender.class);

    private static final String MODERATED_STATUS = "moderated";

    @Autowired
    private UnisenderRestClient unisenderRestClient;

    @Autowired
    private ISentMessageService sentMessageService;

    @Autowired
    private ISendingAccountService accountService;

    /**
     * Send sms.
     *
     * @param message sms to send
     * @return sending response
     */
    @Override
    public List<SendingStatus> send(Message message) {
        List<SendingStatus> response = new ArrayList<>();
        Map<Params, SendingAccountParam> params = getAccountParams(message.getSendingAccountId());
        String token = params.get(Params.TOKEN).getValue();
        Integer messageId = getMessageId(token, message.getText());
        if (new Integer(0).equals(messageId)) {
            return null;
        }
        if (!checkStatus(token, messageId)) {
            return null;
        }
        for (Recipient recipient : message.getRecipients()) {
            for (String phone : recipient.getPhones()) {
                SendingStatus status = new SendingStatus();
                status.setRecipientFullName(recipient.getFullName());
                status.setContactInfo(phone);
                try {
                    SendSmsRequest sendRequest = new SendSmsRequest();
                    sendRequest.setToken(token);
                    sendRequest.setMessageId(messageId);
                    sendRequest.setPhone(phone);
                    unisenderRestClient.sendSms(sendRequest);
                    SentMessage sentMessage = new SentMessage();
                    sentMessage.setSentDate(new Date());
                    sentMessage.setRecipientId(recipient.getId());
                    sentMessage.setRecipientSource(recipient.getRecipientSource());
                    sentMessage.setRecipientFullName(recipient.getFullName());
                    sentMessage.setContactInfo(phone);
                    sentMessage.setSubject(message.getSubject());
                    sentMessage.setText(message.getText());
                    sentMessage.setSendingType(SendingType.SMS);
                    sentMessageService.saveOrUpdate(sentMessage);
                    status.setDescription("Sms sent successfully");
                    LOGGER.info("Sms sent successfully, phone number = " + phone);
                } catch (Exception e) {
                    status.setDescription(e.getClass().getName());
                    LOGGER.error("An error occurred while sending email", e);
                }
                response.add(status);
            }
        }
        return response;
    }

    @Override
    public Set<SendingServices> getSendingServices() {
        return EnumSet.of(SendingServices.UNISENDER_BY);
    }

    @Override
    public SendingType getSendingType() {
        return SendingType.SMS;
    }

    private Integer getMessageId(String token, String text) {
        try {
            BaseUnisenderRequest messageListRequest = new BaseUnisenderRequest();
            messageListRequest.setToken(token);
            List<GetMessageListResult> results = unisenderRestClient.getMessageList(messageListRequest).getResult();
            for (GetMessageListResult result : results) {
                if (text.equals(result.getMessage())) {
                    return result.getMessageId();
                }
            }
            CreateSmsMessageRequest createSmsRequest = new CreateSmsMessageRequest();
            createSmsRequest.setToken(token);
            createSmsRequest.setMessage(text);
            return unisenderRestClient.createSmsMessage(createSmsRequest).getMessageId();
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean checkStatus(String token, Integer messageId) {
        try {
            CheckSmsMessageStatusRequest checkRequest = new CheckSmsMessageStatusRequest();
            checkRequest.setToken(token);
            checkRequest.setMessageId(messageId);
            CheckSmsMessageStatusResponse checkResponse = unisenderRestClient.checkSmsMessageStatus(checkRequest);
            return MODERATED_STATUS.equals(checkResponse.getStatus());
        } catch (Exception e) {
            return false;
        }
    }

    private Map<Params, SendingAccountParam> getAccountParams(String accountId) {
        Map<Params, SendingAccountParam> params = new HashMap<>();
        SendingAccount account = (SendingAccount) accountService.getById(accountId);
        for (SendingAccountParam param : account.getAccountParams()) {
            params.put(Params.valueOf(param.getParam().getName().toUpperCase()), param);
        }
        return params;
    }

}
