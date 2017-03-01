package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.CLIENTS;
import static com.zburzhynski.jsender.api.domain.View.MESSAGE_TEMPLATES;
import static com.zburzhynski.jsender.api.domain.View.SENDING;
import static com.zburzhynski.jsender.api.domain.View.SENT_MESSAGES;
import static com.zburzhynski.jsender.api.domain.View.SETTINGS;
import com.zburzhynski.jsender.impl.util.BeanUtils;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * Main menu bean.
 * <p/>
 * Date: 07.02.2017
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@SessionScoped
public class MainMenuBean implements Serializable {

    private static final String CLIENT_BEAN = "clientBean";

    private static final String MESSAGE_TEMPLATE_BEAN = "messageTemplateBean";

    @ManagedProperty(value = "#{sendingBean}")
    private SendingBean sendingBean;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Redirects to clients.xhtml page.
     *
     * @return path to redirect
     */
    public String clients() {
        ClientBean clientBean = BeanUtils.getSessionBean(CLIENT_BEAN);
        if (clientBean != null) {
            clientBean.setRedirectFrom(CLIENTS);
        }
        return CLIENTS.getPath();
    }

    /**
     * Redirects to sending.xhtml page.
     *
     * @return path to redirect
     */
    public String sending() {
        sendingBean.getMessageToSend().setFrom(settingBean.getOrganizationName());
        return SENDING.getPath();
    }

    /**
     * Redirects to sent_messages.xhtml page.
     *
     * @return path to redirect
     */
    public String sentMessages() {
        return SENT_MESSAGES.getPath();
    }

    /**
     * Redirects to message templates.xhtml page.
     *
     * @return path to redirect
     */
    public String messageTemplates() {
        MessageTemplateBean messageTemplateBean = BeanUtils.getSessionBean(MESSAGE_TEMPLATE_BEAN);
        if (messageTemplateBean != null) {
            messageTemplateBean.setRedirectFrom(MESSAGE_TEMPLATES);
        }
        return MESSAGE_TEMPLATES.getPath();
    }

    /**
     * Redirects to settings.xhtml page.
     *
     * @return path to redirect
     */
    public String settings() {
        return SETTINGS.getPath();
    }

    public void setSendingBean(SendingBean sendingBean) {
        this.sendingBean = sendingBean;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

}
