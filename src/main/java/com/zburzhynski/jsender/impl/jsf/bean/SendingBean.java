package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.CommonConstant.COMMA;
import static com.zburzhynski.jsender.api.domain.CommonConstant.LEFT_PARENTHESIS;
import static com.zburzhynski.jsender.api.domain.CommonConstant.RIGHT_PARENTHESIS;
import static com.zburzhynski.jsender.api.domain.CommonConstant.SPACE;
import static com.zburzhynski.jsender.api.domain.View.MESSAGE_TEMPLATES;
import static com.zburzhynski.jsender.api.domain.View.RECIPIENTS;
import static com.zburzhynski.jsender.api.domain.View.SENDING;
import static com.zburzhynski.jsender.api.domain.View.SENDING_STATUS;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jsender.api.domain.Params;
import com.zburzhynski.jsender.api.domain.ResponseStatus;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.exception.SendingException;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.domain.SendingAccountParam;
import com.zburzhynski.jsender.impl.jsf.validator.SendingValidator;
import com.zburzhynski.jsender.impl.rest.client.UnisenderRestClient;
import com.zburzhynski.jsender.impl.rest.domain.PatientDto;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsResponse;
import com.zburzhynski.jsender.impl.sender.MessageSender;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Sending bean.
 * <p/>
 * Date: 12.02.2017
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@SessionScoped
public class SendingBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendingBean.class);

    private static final String GROWL_ID = "sendingStatusForm:sendingMessages";

    private static final int SECOND_SCALE = 1000;

    private int tabIndex;

    private SendingStatus sendingStatus;

    private Message messageToSend;

    private List<SendingStatus> sendingStatuses;

    private List<Client> recipients = new ArrayList<>();

    @ManagedProperty(value = "#{recipientBean}")
    private RecipientBean recipientBean;

    @ManagedProperty(value = "#{messageTemplateBean}")
    private MessageTemplateBean messageTemplateBean;

    @ManagedProperty(value = "#{messageSender}")
    private MessageSender messageSender;

    @ManagedProperty(value = "#{sendingValidator}")
    private SendingValidator sendingValidator;

    @ManagedProperty(value = "#{sendingAccountListBean}")
    private SendingAccountListBean sendingAccountListBean;

    @ManagedProperty(value = "#{sendingAccountService}")
    private ISendingAccountService accountService;

    @ManagedProperty(value = "#{unisenderRestClient}")
    private UnisenderRestClient unisenderRestClient;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader reader;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        createMessage();
    }

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
                    if (ResponseStatus.SENDING.equals(status.getStatus())) {
                        CheckSmsRequest request = new CheckSmsRequest();
                        request.setSmsId(Integer.valueOf(status.getId()));
                        request.setToken(token);
                        CheckSmsResponse smsResponse = unisenderRestClient.checkSms(request);
                        if (!new Long(0).equals(smsResponse.getDelivered())) {
                            status.setDeliveryDate(new Date(smsResponse.getDelivered() * SECOND_SCALE));
                            status.setStatus(ResponseStatus.OK);
                        }
                    }
                }
            }
        } catch (Exception e) {
            addMessage("sending.errorRefreshingStatusesMessage");
        }
    }

    /**
     * Creates new message.
     */
    public void createMessage() {
        messageToSend = new Message();
        recipients.clear();
        tabIndex = 0;
        messageToSend.setFrom(settingBean.getOrganizationName());
        sendingAccountListBean.findByServiceSendingType(null);
    }

    /**
     * Adds recipients.
     *
     * @return path for navigation
     */
    public String addRecipients() {
        recipientBean.setRedirectFrom(SENDING);
        recipientBean.setSelectedRecipients(new ArrayList<PatientDto>());
        return RECIPIENTS.getPath();
    }

    /**
     * Removes recipient.
     *
     * @param recipient recipient to remove
     * @return path for navigation
     */
    public String removeRecipient(Recipient recipient) {
        messageToSend.removeRecipient(recipient);
        return SENDING.getPath();
    }

    /**
     * Removes all recipients.
     *
     * @return path for navigation
     */
    public String removeAllRecipients() {
        messageToSend.getRecipients().clear();
        return SENDING.getPath();
    }

    /**
     * Selects message template.
     *
     * @return path for navigation
     */
    public String selectMessageTemplate() {
        messageTemplateBean.setRedirectFrom(SENDING);
        messageTemplateBean.setSelectedMessageTemplate(null);
        return MESSAGE_TEMPLATES.getPath();
    }

    /**
     * Sends message.
     *
     * @return path for navigation
     */
    public String send() {
        boolean valid = sendingValidator.validate(messageToSend);
        if (!valid) {
            return null;
        }
        try {
            sendingStatuses = messageSender.send(messageToSend);
        } catch (SendingException e) {
            addFlashMessage(e.getMessage());
        }
        return SENDING_STATUS.getPath();
    }

    /**
     * Select current sendingStatus.
     *
     * @param status sending sendingStatus to select
     */
    public void selectSentStatus(SendingStatus status) {
        this.sendingStatus = status;
    }

    /**
     * Gets phone numbers description.
     *
     * @param recipient recipient
     * @return phone numbers description
     */
    public String getPhoneNumberDescription(Recipient recipient) {
        return StringUtils.join(recipient.getPhones(), COMMA + SPACE);
    }

    /**
     * Gets emails description.
     *
     * @param recipient recipient
     * @return emails description
     */
    public String getEmailDescription(Recipient recipient) {
        return StringUtils.join(recipient.getEmails(), COMMA + SPACE);
    }

    /**
     * Gets sending account description.
     *
     * @param account sending account
     * @return account description
     */
    public String getSendingAccountDescription(SendingAccount account) {
        return StringUtils.isBlank(account.getDescription()) ? account.getSendingService().getName() :
            account.getSendingService().getName() + SPACE + LEFT_PARENTHESIS + account.getDescription() +
                RIGHT_PARENTHESIS;
    }

    /**
     * Sending type change listener.
     */
    public void sendingTypeChangeListener() {
        sendingAccountListBean.findByServiceSendingType(messageToSend.getSendingType());
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public SendingStatus getSendingStatus() {
        return sendingStatus;
    }

    public void setSendingStatus(SendingStatus sendingStatus) {
        this.sendingStatus = sendingStatus;
    }

    public Message getMessageToSend() {
        return messageToSend;
    }

    public void setMessageToSend(Message messageToSend) {
        this.messageToSend = messageToSend;
    }

    public List<SendingStatus> getSendingStatuses() {
        return sendingStatuses;
    }

    public List<Client> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Client> recipients) {
        this.recipients = recipients;
    }

    public Integer getRowCount() {
        return settingBean.getSendingRecipientsPerPage();
    }

    public void setRecipientBean(RecipientBean recipientBean) {
        this.recipientBean = recipientBean;
    }

    public void setMessageTemplateBean(MessageTemplateBean messageTemplateBean) {
        this.messageTemplateBean = messageTemplateBean;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void setSendingValidator(SendingValidator sendingValidator) {
        this.sendingValidator = sendingValidator;
    }

    public void setSendingAccountListBean(SendingAccountListBean sendingAccountListBean) {
        this.sendingAccountListBean = sendingAccountListBean;
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

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

    /**
     * Adds localisation message to flash context.
     *
     * @param message localisation message
     */
    private void addFlashMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(reader.readProperty(message), StringUtils.EMPTY);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(GROWL_ID, facesMessage);
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
