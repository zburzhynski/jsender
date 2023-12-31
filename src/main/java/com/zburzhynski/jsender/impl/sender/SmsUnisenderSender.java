package com.zburzhynski.jsender.impl.sender;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import com.zburzhynski.jsender.api.domain.Params;
import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.domain.SendingStatus;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.MessageStatus;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.dto.SendingResponse;
import com.zburzhynski.jsender.api.exception.EncryptionException;
import com.zburzhynski.jsender.api.sender.ISender;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.api.service.ISentMessageService;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.domain.SendingAccountParam;
import com.zburzhynski.jsender.impl.domain.SentMessage;
import com.zburzhynski.jsender.impl.domain.Setting;
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
import com.zburzhynski.jsender.impl.rest.exception.unisender.LicenseException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.LimitExceededException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.ObjectNotFoundException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.UndefinedException;
import com.zburzhynski.jsender.impl.util.CryptoUtils;
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

    @Autowired
    private ISettingService settingService;

    @Override
    public SendingResponse send(Message message) {
        SendingResponse response = new SendingResponse();
        Map<Params, SendingAccountParam> params = getAccountParams(message.getSendingAccountId());
        String token = params.get(Params.TOKEN).getValue();
        Integer alphanameId = Integer.valueOf(params.get(Params.ALPHANAME_ID).getValue());
        long sendingDelay = Long.parseLong(((Setting) settingService.getByName(Settings.SMS_SENDING_DELAY)).getValue());
        try {
            boolean countBalance = isCountBalance();
            for (Recipient recipient : message.getRecipients()) {
                try {
                    String smsText = textHelper.prepareSmsText(message.getText(), recipient);
                    Integer messageId = createSmsMessage(token, alphanameId, smsText);
                    CheckSmsMessageStatusResponse statusResponse = getMessageStatus(token, messageId);
                    if (MODERATED_STATUS.equals(statusResponse.getStatus())) {
                        for (String phone : recipient.getPhones()) {
                            try {
                                checkBalance(statusResponse, countBalance);
                                SendSmsRequest sendRequest = new SendSmsRequest();
                                sendRequest.setToken(token);
                                sendRequest.setMessageId(messageId);
                                sendRequest.setPhone(preparePhone(phone));
                                SendSmsResponse smsResponse = unisenderRestClient.sendSms(sendRequest);
                                if (smsResponse != null) {
                                    updateBalance(statusResponse, countBalance);
                                    saveMessage(recipient, message, phone, smsText);
                                    response.addMessageStatus(createSentStatus(smsResponse.getSmsId().toString(),
                                        recipient, phone, message.getText(), statusResponse.getParts()));
                                }
                            } catch (IncorrectPhoneNumberException e) {
                                response.addMessageStatus(createErrorStatus(recipient, phone, message.getText(),
                                    "smsUnisenderSender.incorrectPhoneNumber"));
                            }
                        }
                    }
                    if (sendingDelay > 0) {
                        Thread.sleep(sendingDelay);
                    }
                } catch (MessageToLongException e) {
                    response.addMessageStatuses(createErrorStatus(recipient, message.getText(),
                        "smsUnisenderSender.messageToLong"));
                } catch (UndefinedException e) {
                    response.addMessageStatuses(createErrorStatus(recipient, message.getText(),
                        "smsUnisenderSender.undefinedError"));
                } catch (ObjectNotFoundException e) {
                    LOGGER.warn("Message not found", e);
                } catch (InterruptedException e) {
                    LOGGER.error("Interrupted exception", e);
                }
            }
        } catch (InvalidTokenException e) {
            response.setErrorMessage("smsUnisenderSender.invalidToken");
        } catch (AlphanameIncorrectException e) {
            response.setErrorMessage("smsUnisenderSender.alphanameIncorrect");
        } catch (BillingException e) {
            response.setErrorMessage("smsUnisenderSender.billingError");
        } catch (AccessDeniedException e) {
            response.setErrorMessage("smsUnisenderSender.accessDenied");
        } catch (LimitExceededException e) {
            response.setErrorMessage("smsUnisenderSender.limitExceeded");
        } catch (HostUnavailableException e) {
            response.setErrorMessage("smsUnisender.hostUnavailableException");
        } catch (LicenseException e) {
            response.setErrorMessage("smsUnisender.licenseException");
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

    private CheckSmsMessageStatusResponse getMessageStatus(String token, Integer messageId) throws UndefinedException,
        InvalidTokenException, ObjectNotFoundException, HostUnavailableException {
        CheckSmsMessageStatusRequest request = new CheckSmsMessageStatusRequest();
        request.setToken(token);
        request.setMessageId(messageId);
        return unisenderRestClient.checkSmsMessageStatus(request);
    }

    private String preparePhone(String phone) {
        return phone.replaceFirst("\\+", EMPTY);
    }

    private boolean isCountBalance() throws LicenseException {
        try {
            return Boolean.parseBoolean(CryptoUtils.decrypt(((Setting) settingService.getByName(Settings.CCC))
                .getValue()));
        } catch (EncryptionException e) {
            throw new LicenseException();
        }
    }

    private void checkBalance(CheckSmsMessageStatusResponse status, boolean countBalance) throws LimitExceededException,
        LicenseException {
        try {
            if (countBalance) {
                Integer aaa = CryptoUtils.decryptInt(((Setting) settingService.getByName(Settings.AAA)).getValue());
                Integer bbb = CryptoUtils.decryptInt(((Setting) settingService.getByName(Settings.BBB)).getValue());
                if (aaa == null || bbb == null) {
                    throw new LicenseException();
                }
                if (aaa + status.getParts() > bbb) {
                    throw new LimitExceededException();
                }
            }
        } catch (EncryptionException e) {
            throw new LicenseException();
        }
    }

    private void updateBalance(CheckSmsMessageStatusResponse status, boolean countBalance) throws LicenseException {
        try {
            if (countBalance) {
                Setting setting = (Setting) settingService.getByName(Settings.AAA);
                Integer aaa = CryptoUtils.decryptInt(setting.getValue());
                Integer bbb = CryptoUtils.decryptInt(((Setting) settingService.getByName(Settings.BBB)).getValue());
                if (aaa == null || bbb == null) {
                    throw new LicenseException();
                }
                Integer value = aaa + status.getParts();
                setting.setValue(CryptoUtils.encrypt(value.toString()));
                settingService.saveOrUpdate(setting);
            }
        } catch (EncryptionException e) {
            throw new LicenseException();
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

    private MessageStatus createSentStatus(String id, Recipient recipient, String phone, String text, Integer parts) {
        MessageStatus status = createStatus(id, recipient, phone, text, SendingStatus.SENT, null);
        status.setParts(parts);
        return status;
    }

    private List<MessageStatus> createErrorStatus(Recipient recipient, String text, String message) {
        List<MessageStatus> statuses = new ArrayList<>();
        for (String phone : recipient.getPhones()) {
            statuses.add(createStatus(null, recipient, phone, text, SendingStatus.ERROR, message));
        }
        return statuses;
    }

    private MessageStatus createErrorStatus(Recipient recipient, String phone, String text, String message) {
        return createStatus(null, recipient, phone, text, SendingStatus.ERROR, message);
    }

    private MessageStatus createStatus(String id, Recipient recipient, String phone, String text, SendingStatus status,
                                       String message) {
        MessageStatus messageStatus = new MessageStatus();
        messageStatus.setId(id);
        messageStatus.setRecipientFullName(recipient.getFullName());
        messageStatus.setContactInfo(phone);
        messageStatus.setText(text);
        messageStatus.setSendingDate(new Date());
        messageStatus.setStatus(status);
        messageStatus.setDescription(isNotBlank(message) ? propertyReader.readProperty(message) : null);
        return messageStatus;
    }

    private void saveMessage(Recipient recipient, Message message, String phone, String text) {
        SentMessage sentMessage = new SentMessage();
        sentMessage.setSentDate(new Date());
        sentMessage.setRecipientId(recipient.getId());
        sentMessage.setRecipientSource(recipient.getRecipientSource());
        sentMessage.setRecipientFullName(recipient.getFullName());
        sentMessage.setContactInfo(phone);
        sentMessage.setSubject(message.getSubject());
        sentMessage.setText(text);
        sentMessage.setSendingType(SendingType.SMS);
        sentMessageService.saveOrUpdate(sentMessage);
    }

}
