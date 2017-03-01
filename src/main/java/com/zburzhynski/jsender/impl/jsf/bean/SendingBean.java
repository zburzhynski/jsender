package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.CommonConstant.COMMA;
import static com.zburzhynski.jsender.api.domain.CommonConstant.SPACE;
import static com.zburzhynski.jsender.api.domain.View.MESSAGE_TEMPLATES;
import static com.zburzhynski.jsender.api.domain.View.RECIPIENTS;
import static com.zburzhynski.jsender.api.domain.View.SENDING;
import static com.zburzhynski.jsender.api.domain.View.SENDING_STATUS;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.service.ISender;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.domain.ContactInfoEmail;
import com.zburzhynski.jsender.impl.domain.ContactInfoPhone;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private static final String RECIPIENTS_NOT_SPECIFIED = "sendingValidator.recipientsNotSpecified";

    private static final String MESSAGE_TEMPLATE_BEAN = "messageTemplateBean";

    private int tabIndex;

    private Message messageToSend = new Message();

    private SendingType sendingType;

    private Map<Recipient, String> sendingStatus;

    private List<Client> recipients = new ArrayList<>();

    @ManagedProperty(value = "#{recipientBean}")
    private RecipientBean recipientBean;

    @ManagedProperty(value = "#{messageTemplateBean}")
    private MessageTemplateBean messageTemplateBean;

    @ManagedProperty(value = "#{emailSender}")
    private ISender emailSender;

    @ManagedProperty(value = "#{smsSender}")
    private ISender smsSender;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader reader;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Sends message.
     *
     * @return path for navigation
     */
    public String send() {
        List<Recipient> contacts = new ArrayList<>();
        if (SendingType.SMS.equals(sendingType)) {
            for (Client recipient : recipients) {
                for (ContactInfoPhone phone : recipient.getContactInfo().getPhones()) {
                    Recipient contact = new Recipient();
                    contact.setSurname(recipient.getPerson().getSurname());
                    contact.setName(recipient.getPerson().getName());
                    contact.setPatronymic(recipient.getPerson().getPatronymic());
                    contact.setContactInfo(phone.getFullNumber());
                    contact.setFullName(recipient.getPerson().getFullName());
                    contacts.add(contact);
                }
            }
            if (CollectionUtils.isEmpty(contacts)) {
                addMessage(RECIPIENTS_NOT_SPECIFIED);
                return null;
            }
            messageToSend.setRecipients(contacts);
            sendingStatus = smsSender.send(messageToSend);
        } else if (SendingType.EMAIL.equals(sendingType)) {
            for (Client recipient : recipients) {
                for (ContactInfoEmail email : recipient.getContactInfo().getEmails()) {
                    Recipient contact = new Recipient();
                    contact.setSurname(recipient.getPerson().getSurname());
                    contact.setName(recipient.getPerson().getName());
                    contact.setPatronymic(recipient.getPerson().getPatronymic());
                    contact.setContactInfo(email.getAddress());
                    contact.setFullName(recipient.getPerson().getFullName());
                    contacts.add(contact);
                }
            }
            if (CollectionUtils.isEmpty(contacts)) {
                addMessage(RECIPIENTS_NOT_SPECIFIED);
                return null;
            }
            messageToSend.setRecipients(contacts);
            sendingStatus = emailSender.send(messageToSend);
        }
        return SENDING_STATUS.getPath();
    }

    /**
     * Gets phone numbers description.
     *
     * @param recipient recipient
     * @return phone numbers description
     */
    public String getPhoneNumberDescription(Client recipient) {
        List<String> phoneNumbers = new ArrayList<>();
        for (ContactInfoPhone phone : recipient.getContactInfo().getPhones()) {
            phoneNumbers.add(phone.getFullNumber());
        }
        return StringUtils.join(phoneNumbers, COMMA + SPACE);
    }

    /**
     * Gets email description.
     *
     * @param recipient recipient
     * @return emails description
     */
    public String getEmailDescription(Client recipient) {
        List<String> emails = new ArrayList<>();
        for (ContactInfoEmail email : recipient.getContactInfo().getEmails()) {
            emails.add(email.getAddress());
        }
        return StringUtils.join(emails, COMMA + SPACE);
    }

    /**
     * Adds recipients.
     *
     * @return path for navigation
     */
    public String addRecipients() {
        recipientBean.setRedirectFrom(SENDING);
        recipientBean.setSelectedClients(new ArrayList<Client>());
        return RECIPIENTS.getPath();
    }

    /**
     * Removes recipient.
     *
     * @param recipient recipient to remove
     * @return path for navigation
     */
    public String removeRecipient(Client recipient) {
        recipients.remove(recipient);
        return SENDING.getPath();
    }

    /**
     * Removes all recipients.
     *
     * @return path for navigation
     */
    public String removeAllRecipients() {
        recipients.clear();
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

    public SendingType getSendingType() {
        return sendingType;
    }

    public void setSendingType(SendingType sendingType) {
        this.sendingType = sendingType;
    }

    public Set<Map.Entry<Recipient, String>> getSendingStatus() {
        return sendingStatus.entrySet();
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
