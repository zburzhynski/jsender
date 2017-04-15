package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.MESSAGE_STATUS;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.exception.SendingException;
import com.zburzhynski.jsender.impl.sender.MessageSender;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import com.zburzhynski.jsender.impl.util.TextHelper;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Sending preview bean.
 * <p/>
 * Date: 10.04.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@ViewScoped
public class SendingPreviewBean implements Serializable {

    private static final String GROWL_ID = "sendingStatusForm:sendingMessages";

    private Recipient recipient;

    private String text;

    @ManagedProperty(value = "#{sendingBean}")
    private SendingBean sendingBean;

    @ManagedProperty(value = "#{sendingStatusBean}")
    private SendingStatusBean sendingStatusBean;

    @ManagedProperty(value = "#{textHelper}")
    private TextHelper textHelper;

    @ManagedProperty(value = "#{messageSender}")
    private MessageSender messageSender;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader reader;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Init bean state.
     */
    @PostConstruct
    public void init() {
        recipient = sendingBean.getMessageToSend().getRecipients().get(0);
        prepareText();
    }

    /**
     * Sends message.
     *
     * @return path for navigation
     */
    public String send() {
        try {
            sendingStatusBean.setMessageToSend(sendingBean.getMessageToSend());
            sendingStatusBean.setSendingResponse(messageSender.send(sendingBean.getMessageToSend()));
        } catch (SendingException e) {
            addFlashMessage(e.getMessage());
        }
        return MESSAGE_STATUS.getPath();
    }

    /**
     * Select recipient listener.
     *
     * @param event {@link SelectEvent} select event
     */
    public void selectRecipientListener(SelectEvent event) {
        recipient = (Recipient) event.getObject();
        prepareText();
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSendingBean(SendingBean sendingBean) {
        this.sendingBean = sendingBean;
    }

    public void setSendingStatusBean(SendingStatusBean sendingStatusBean) {
        this.sendingStatusBean = sendingStatusBean;
    }

    public void setTextHelper(TextHelper textHelper) {
        this.textHelper = textHelper;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void setReader(PropertyReader reader) {
        this.reader = reader;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

    private void prepareText() {
        switch (sendingBean.getMessageToSend().getSendingType()) {
            case SMS:
                text = textHelper.prepareSmsText(sendingBean.getMessageToSend().getText(), recipient);
                break;
            case EMAIL:
                text = textHelper.prepareEmailText(sendingBean.getMessageToSend().getText(), recipient);
                break;
            default:
                break;
        }
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

}
