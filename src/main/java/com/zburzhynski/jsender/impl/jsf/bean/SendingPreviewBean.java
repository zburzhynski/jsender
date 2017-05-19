package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.Settings.AAA;
import static com.zburzhynski.jsender.api.domain.Settings.BBB;
import static com.zburzhynski.jsender.api.domain.Settings.CCC;
import static com.zburzhynski.jsender.api.domain.View.MESSAGE_STATUS;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.dto.SendingResponse;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;
import com.zburzhynski.jsender.impl.sender.MessageSender;
import com.zburzhynski.jsender.impl.util.CryptoUtils;
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

    private boolean countSmsBalance;

    private int smsBalance;

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

    @ManagedProperty(value = "#{settingService}")
    private ISettingService settingService;

    /**
     * Init bean state.
     */
    @PostConstruct
    public void init() {
        recipient = sendingBean.getMessageToSend().getRecipients().get(0);
        prepareText();
        refreshSmsBalance();
    }

    /**
     * Sends message.
     *
     * @return path for navigation
     */
    public String send() {
        sendingStatusBean.setMessageToSend(sendingBean.getMessageToSend());
        SendingResponse response = messageSender.send(sendingBean.getMessageToSend());
        sendingStatusBean.setSendingResponse(response);
        if (StringUtils.isNotEmpty(response.getErrorMessage())) {
            addFlashMessage(response.getErrorMessage());
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

    public boolean isCountSmsBalance() {
        return countSmsBalance;
    }

    public int getSmsBalance() {
        return smsBalance;
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

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }

    private void refreshSmsBalance() {
        try {
            countSmsBalance =  Boolean.parseBoolean(CryptoUtils.decrypt(((Setting) settingService.getByName(CCC))
                .getValue()));
        } catch (Exception e) {
            countSmsBalance = true;
        }
        if (countSmsBalance) {
            try {
                Integer aaa = CryptoUtils.decryptInt(((Setting) settingService.getByName(AAA)).getValue());
                Integer bbb = CryptoUtils.decryptInt(((Setting) settingService.getByName(BBB)).getValue());
                if (aaa != null && bbb != null) {
                    smsBalance = bbb - aaa;
                } else {
                    smsBalance = 0;
                }
            } catch (Exception e) {
                smsBalance =  0;
            }
        }
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
