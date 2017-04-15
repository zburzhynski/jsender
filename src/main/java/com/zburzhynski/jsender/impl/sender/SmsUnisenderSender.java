package com.zburzhynski.jsender.impl.sender;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import com.zburzhynski.jsender.api.domain.Params;
import com.zburzhynski.jsender.api.domain.ResponseStatus;
import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.exception.SendingException;
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
import com.zburzhynski.jsender.impl.rest.exception.unisender.HostUnavailableException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.IncorrectPhoneNumberException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.InvalidTokenException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.LimitExceededException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.ObjectNotFoundException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.UndefinedException;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import com.zburzhynski.jsender.impl.util.TextHelper;
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
    private TextHelper textHelper;

    @Autowired
    private UnisenderRestClient unisenderRestClient;

    @Autowired
    private ISentMessageService sentMessageService;

    @Autowired
    private ISendingAccountService accountService;

    @Autowired
    private PropertyReader propertyReader;

    @Override
    public List<SendingStatus> send(Message message) throws SendingException {
        List<SendingStatus> response = new ArrayList<>();
        Map<Params, SendingAccountParam> params = getAccountParams(message.getSendingAccountId());
        String token = params.get(Params.TOKEN).getValue();
        Integer alphanameId = Integer.valueOf(params.get(Params.ALPHANAME_ID).getValue());
        try {
            for (Recipient recipient : message.getRecipients()) {
                try {
                    String smsText = textHelper.prepareSmsText(message.getText(), recipient);
                    Integer messageId = createSmsMessage(token, alphanameId, smsText);
                    if (isMessageModerated(token, messageId)) {
                        for (String phone : recipient.getPhones()) {
                            try {
                                SendSmsRequest sendRequest = new SendSmsRequest();
                                sendRequest.setToken(token);
                                sendRequest.setMessageId(messageId);
                                sendRequest.setPhone(preparePhone(phone));
                                SendSmsResponse smsResponse = unisenderRestClient.sendSms(sendRequest);
                                if (smsResponse != null) {
                                    saveMessage(recipient, message, phone, smsText);
                                    response.add(createSentStatus(smsResponse.getSmsId().toString(),
                                        recipient, phone));
                                }
                            } catch (IncorrectPhoneNumberException e) {
                                response.add(createErrorStatus(recipient, phone,
                                    "smsUnisenderSender.incorrectPhoneNumber"));
                            }
                        }
                    }
                } catch (MessageToLongException e) {
                    response.addAll(createErrorStatus(recipient, "smsUnisenderSender.messageToLong"));
                } catch (UndefinedException e) {
                    response.addAll(createErrorStatus(recipient, "smsUnisenderSender.undefinedError"));
                } catch (ObjectNotFoundException e) {
                    LOGGER.warn("Message not found", e);
                }
            }
        } catch (InvalidTokenException e) {
            throw new SendingException("smsUnisenderSender.invalidToken");
        } catch (AlphanameIncorrectException e) {
            throw new SendingException("smsUnisenderSender.alphanameIncorrect");
        } catch (BillingException e) {
            throw new SendingException("smsUnisenderSender.billingError");
        } catch (AccessDeniedException e) {
            throw new SendingException("smsUnisenderSender.accessDenied");
        } catch (LimitExceededException e) {
            throw new SendingException("smsUnisenderSender.limitExceeded");
        } catch (HostUnavailableException e) {
            throw new SendingException("smsUnisender.hostUnavailableException");
        }
        return response;
    }

    @Override
    public Set<SendingServices> getSendingServices() {
        return EnumSet.of(SendingServices.SMS_UNISENDER_BY);
    }

    @Override
    public SendingType getSendingType() {
        return SendingType.SMS;
    }

    private Integer createSmsMessage(String token, Integer alphanameId, String text) throws InvalidTokenException,
        MessageToLongException, UndefinedException, AlphanameIncorrectException, HostUnavailableException {
        try {
            CreateSmsMessageRequest request = new CreateSmsMessageRequest();
            request.setToken(token);
            request.setAlphanameId(alphanameId != 0 ? alphanameId : null);
            request.setMessage(text);
            CreateSmsMessageResponse response = unisenderRestClient.createSmsMessage(request);
            return response.getMessageId();
        } catch (MessageAlreadyExistException e) {
            return e.getMessageId();
        }
    }

    private boolean isMessageModerated(String token, Integer messageId) throws UndefinedException,
        InvalidTokenException, ObjectNotFoundException, HostUnavailableException {
        CheckSmsMessageStatusRequest request = new CheckSmsMessageStatusRequest();
        request.setToken(token);
        request.setMessageId(messageId);
        CheckSmsMessageStatusResponse response = unisenderRestClient.checkSmsMessageStatus(request);
        return MODERATED_STATUS.equals(response.getStatus());
    }

    private String preparePhone(String phone) {
        return phone.replaceFirst("\\+", EMPTY);
    }

    private Map<Params, SendingAccountParam> getAccountParams(String accountId) {
        Map<Params, SendingAccountParam> params = new HashMap<>();
        SendingAccount account = (SendingAccount) accountService.getById(accountId);
        for (SendingAccountParam param : account.getAccountParams()) {
            params.put(Params.valueOf(param.getParam().getName().toUpperCase()), param);
        }
        return params;
    }

    private SendingStatus createSentStatus(String id, Recipient recipient, String phone) {
        return createStatus(id, recipient, phone, ResponseStatus.SENT, null);
    }

    private List<SendingStatus> createErrorStatus(Recipient recipient, String message) {
        List<SendingStatus> statuses = new ArrayList<>();
        for (String phone : recipient.getPhones()) {
            statuses.add(createStatus(null, recipient, phone, ResponseStatus.ERROR, message));
        }
        return statuses;
    }

    private SendingStatus createErrorStatus(Recipient recipient, String phone, String message) {
        return createStatus(null, recipient, phone, ResponseStatus.ERROR, message);
    }

    private SendingStatus createStatus(String id, Recipient recipient, String phone, ResponseStatus status,
                                       String message) {
        SendingStatus sendingStatus = new SendingStatus();
        sendingStatus.setId(id);
        sendingStatus.setRecipientFullName(recipient.getFullName());
        sendingStatus.setContactInfo(phone);
        sendingStatus.setSendingDate(new Date());
        sendingStatus.setStatus(status);
        sendingStatus.setDescription(isNotBlank(message) ? propertyReader.readProperty(message) : null);
        return sendingStatus;
    }

    private void saveMessage(Recipient recipient, Message message, String phone, String smsText) {
        SentMessage sentMessage = new SentMessage();
        sentMessage.setSentDate(new Date());
        sentMessage.setRecipientId(recipient.getId());
        sentMessage.setRecipientSource(recipient.getRecipientSource());
        sentMessage.setRecipientFullName(recipient.getFullName());
        sentMessage.setContactInfo(phone);
        sentMessage.setSubject(message.getSubject());
        sentMessage.setText(smsText);
        sentMessage.setSendingType(SendingType.SMS);
        sentMessageService.saveOrUpdate(sentMessage);
    }

}
