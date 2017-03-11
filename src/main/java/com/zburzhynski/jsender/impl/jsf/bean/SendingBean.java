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
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.sender.ISender;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.domain.ContactInfoEmail;
import com.zburzhynski.jsender.impl.domain.ContactInfoPhone;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.jsf.validator.SendingValidator;
import com.zburzhynski.jsender.impl.rest.domain.PatientDto;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
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

    private int tabIndex;

    private Message messageToSend;

    private List<SendingStatus> sendingStatuses;

    private List<Client> recipients = new ArrayList<>();

    @ManagedProperty(value = "#{recipientBean}")
    private RecipientBean recipientBean;

    @ManagedProperty(value = "#{messageTemplateBean}")
    private MessageTemplateBean messageTemplateBean;

    @ManagedProperty(value = "#{emailSender}")
    private ISender emailSender;

    @ManagedProperty(value = "#{smsSender}")
    private ISender smsSender;

    @ManagedProperty(value = "#{sendingValidator}")
    private SendingValidator sendingValidator;

    @ManagedProperty(value = "#{sendingAccountListBean}")
    private SendingAccountListBean sendingAccountListBean;

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
     * Creates new message.
     */
    public void createMessage() {
        messageToSend = new Message();
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
    //TODO: move to service
    public String send() {
        for (Client client : recipients) {
            Recipient recipient = new Recipient();
            recipient.setId(client.getId());
            recipient.setSurname(client.getPerson().getSurname());
            recipient.setName(client.getPerson().getName());
            recipient.setPatronymic(client.getPerson().getPatronymic());
            switch (messageToSend.getSendingType()) {
                case SMS:
                    for (ContactInfoPhone phone : client.getContactInfo().getPhones()) {
                        recipient.addPhone(phone.getFullNumber());
                    }
                    break;
                case EMAIL:
                    for (ContactInfoEmail email : client.getContactInfo().getEmails()) {
                        recipient.addEmail(email.getAddress());
                    }
                    break;
                default:
            }
            messageToSend.addRecipient(recipient);
        }
        boolean valid = sendingValidator.validate(messageToSend);
        if (!valid) {
            return null;
        }
        switch (messageToSend.getSendingType()) {
            case SMS:
                sendingStatuses = smsSender.send(messageToSend);
                break;
            case EMAIL:
                sendingStatuses = emailSender.send(messageToSend);
                break;
            default:
        }
        return SENDING_STATUS.getPath();
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

    public void setEmailSender(ISender emailSender) {
        this.emailSender = emailSender;
    }

    public void setSmsSender(ISender smsSender) {
        this.smsSender = smsSender;
    }

    public void setSendingValidator(SendingValidator sendingValidator) {
        this.sendingValidator = sendingValidator;
    }

    public void setSendingAccountListBean(SendingAccountListBean sendingAccountListBean) {
        this.sendingAccountListBean = sendingAccountListBean;
    }

    public void setReader(PropertyReader reader) {
        this.reader = reader;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

    /**
     * Adds localisation message to context.
     *
     * @param message localisation message
     */
    protected void addMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(reader.readProperty(message), StringUtils.EMPTY);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.addMessage(null, facesMessage);
    }

}
