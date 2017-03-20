package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.domain.Params;
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
import com.zburzhynski.jsender.impl.rest.exception.unisender.IncorrectPhoneNumberException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.InvalidTokenException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.LimitExceededException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.ObjectNotFoundException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.UndefinedException;
import com.zburzhynski.jsender.impl.service.AbstractSender;
import org.apache.commons.lang3.StringUtils;
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
public class SmsUnisenderSender extends AbstractSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsUnisenderSender.class);

    private static final String HTML_TAG_PATTERN = "\\<.*?>";

    private static final String MODERATED_STATUS = "moderated";

    private static final String MESSAGE_TO_LONG = "smsUnisenderSender.messageToLong";

    private static final String INVALID_TOKEN = "smsUnisenderSender.invalidToken";

    private static final String ALPHANAME_INCORRECT = "smsUnisenderSender.alphanameIncorrect";

    private static final String UNDEFINED_ERROR = "smsUnisenderSender.undefinedError";

    @Autowired
    private UnisenderRestClient unisenderRestClient;

    @Autowired
    private ISentMessageService sentMessageService;

    @Autowired
    private ISendingAccountService accountService;

    @Override
    public List<SendingStatus> send(Message message) throws SendingException {
        List<SendingStatus> response = new ArrayList<>();
        Map<Params, SendingAccountParam> params = getAccountParams(message.getSendingAccountId());
        String token = params.get(Params.TOKEN).getValue();
        try {
            for (Recipient recipient : message.getRecipients()) {
                String smsText = prepareText(message.getText(), recipient);
                Integer messageId = createSmsMessage(token, smsText.replaceAll(HTML_TAG_PATTERN, ""));
                if (isMessageModerated(token, messageId)) {
                    for (String phone : recipient.getPhones()) {
                        try {
                            SendSmsRequest sendRequest = new SendSmsRequest();
                            sendRequest.setToken(token);
                            sendRequest.setMessageId(messageId);
                            sendRequest.setPhone(preparePhone(phone));
                            SendSmsResponse smsResponse = unisenderRestClient.sendSms(sendRequest);
                            if (smsResponse != null) {
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
                        } catch (IncorrectPhoneNumberException e) {
                            LOGGER.error("Incorrect phone number exception", e);
                        } catch (BillingException e) {
                            LOGGER.error("Billing exception", e);
                        } catch (AccessDeniedException e) {
                            LOGGER.error("Access denied exception", e);
                        } catch (LimitExceededException e) {
                            LOGGER.error("Limit exceeded exception", e);
                        } catch (ObjectNotFoundException e) {
                            LOGGER.error("Object not found exception", e);
                        } catch (UndefinedException e) {
                            LOGGER.error("Undefined exception", e);
                        }
                    }
                }
            }
        } catch (MessageToLongException e) {
            throw new SendingException(MESSAGE_TO_LONG);
        } catch (InvalidTokenException e) {
            throw new SendingException(INVALID_TOKEN);
        } catch (AlphanameIncorrectException e) {
            throw new SendingException(ALPHANAME_INCORRECT);
        } catch (UndefinedException e) {
            throw new SendingException(UNDEFINED_ERROR);
        } catch (ObjectNotFoundException e) {
            LOGGER.error("Object not found exception", e);
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

    private String preparePhone(String phone) {
        return phone.replaceFirst("\\+", StringUtils.EMPTY);
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
