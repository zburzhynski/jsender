package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.TemplateTag;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.service.IClientService;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.util.PropertyReader;

import org.apache.commons.lang.StringUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Template replace bean.
 * <p/>
 * Date: 22.02.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@SessionScoped
public class TemplateReplaceBean {

    @ManagedProperty(value = "#{clientService}")
    private IClientService<String, Client> clientService;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader propertyReader;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Formats message template.
     *
     * @param message message to format
     */
    public void formatTemplate(Message message) {
        Client client = clientService.getById(message.getRecipient().getClientId());
        replaceTag(TemplateTag.CLIENT_SURNAME, message, client.getPerson().getSurname());
        replaceTag(TemplateTag.CLIENT_NAME, message, client.getPerson().getName());
        replaceTag(TemplateTag.CLIENT_PATRONYMIC, message, client.getPerson().getPatronymic());
        replaceTag(TemplateTag.CLIENT_FULL_NAME, message, client.getPerson().getFullName());
        replaceTag(TemplateTag.ORGANIZATION_NAME, message, settingBean.getOrganizationName());
        replaceTag(TemplateTag.ORGANIZATION_MOBILE_PHONE_NUMBER, message, settingBean.getOrganizationPhoneNumber());
        replaceTag(TemplateTag.ORGANIZATION_ADDRESS, message, settingBean.getOrganizationAddress());
    }

    public void setClientService(IClientService<String, Client> clientService) {
        this.clientService = clientService;
    }

    public void setPropertyReader(PropertyReader propertyReader) {
        this.propertyReader = propertyReader;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

    private void replaceTag(TemplateTag tag, Message message, String replacement) {
        String formattedText = StringUtils.replace(message.getText(), propertyReader.readProperty(tag.getValue()),
            replacement);
        message.setText(formattedText);
    }

}
