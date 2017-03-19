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
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsMessageStatusRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsMessageStatusResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CreateSmsMessageRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CreateSmsMessageResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.SendSmsRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.SendSmsResponse;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AccessDeniedException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AlphanameIncorrectException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.BillingException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.IncorrectPhoneNumberException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.InvalidTokenException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.LimitExceededException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.ObjectNotFoundException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.UndefinedException;
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
        try {
            Integer messageId = createSmsMessage(token, message.getText());
            if (isMessageModerated(token, messageId)) {
                for (Recipient recipient : message.getRecipients()) {
                    for (String phone : recipient.getPhones()) {
                        try {
                            SendSmsRequest sendRequest = new SendSmsRequest();
                            sendRequest.setToken(token);
                            sendRequest.setMessageId(messageId);
                            sendRequest.setPhone(phone);
                            SendSmsResponse smsResponse = unisenderRestClient.sendSms(sendRequest);
                            if (smsResponse != null) {
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
                            }
                        } catch (IncorrectPhoneNumberException e) {
                            LOGGER.error("Incorrect phone number exception", e);
                        } catch (BillingException e) {
                            LOGGER.error("Billing exception", e);
                        } catch (AccessDeniedException e) {
                            LOGGER.error("Access denied exception", e);
                        } catch (LimitExceededException e) {
                            LOGGER.error("Limit exceeded exception", e);
                        }
                    }
                }
            }
        } catch (ObjectNotFoundException e) {
            LOGGER.error("Object not found exception", e);
        } catch (AlphanameIncorrectException e) {
            LOGGER.error("Alphaname incorrect exception", e);
        } catch (MessageToLongException e) {
            LOGGER.error("Message to long exception", e);
        } catch (InvalidTokenException e) {
            LOGGER.error("Invalid token exception", e);
        } catch (UndefinedException e) {
            LOGGER.error("Undefined exception", e);
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

    private Integer createSmsMessage(String token, String text) throws InvalidTokenException, MessageToLongException,
        UndefinedException, AlphanameIncorrectException {
        try {
            CreateSmsMessageRequest request = new CreateSmsMessageRequest();
            request.setToken(token);
            request.setMessage(text);
            CreateSmsMessageResponse response = unisenderRestClient.createSmsMessage(request);
            return response.getMessageId();
        } catch (MessageAlreadyExistException e) {
            return e.getMessageId();
        }
    }

    private boolean isMessageModerated(String token, Integer messageId) throws UndefinedException,
        InvalidTokenException, ObjectNotFoundException {
        CheckSmsMessageStatusRequest request = new CheckSmsMessageStatusRequest();
        request.setToken(token);
        request.setMessageId(messageId);
        CheckSmsMessageStatusResponse response = unisenderRestClient.checkSmsMessageStatus(request);
        return MODERATED_STATUS.equals(response.getStatus());
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
