package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.domain.TemplateTag;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.repository.IClientRepository;
import com.zburzhynski.jsender.api.repository.ISettingRepository;
import com.zburzhynski.jsender.api.service.IMessagePreparer;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.domain.Setting;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

/**
 * Implementation of {@link IMessagePreparer} interface.
 * <p/>
 * Date: 22.02.2017
 *
 * @author Nikita Shevtsou
 */
@Service("messagePreparer")
@Transactional(readOnly = true)
public class MessagePreparer implements IMessagePreparer {

    @Autowired
    private IClientRepository<String, Client> clientRepository;

    @Autowired
    private PropertyReader propertyReader;

    @Autowired
    private ISettingRepository<String, Setting> settingRepository;

    /**
     * {@inheritDoc}
     */
    public void prepareMessage(Message message) {
        Pattern tagPattern = Pattern.compile("\\{.+}");
        if (tagPattern.matcher(message.getText()).find()) {
            Client client = clientRepository.findById(message.getRecipient().getClientId());
            String organizationName = settingRepository.findByName(Settings.ORGANIZATION_NAME).getValue();
            String organizationMobilePhoneNumber = settingRepository.findByName(
                Settings.ORGANIZATION_MOBILE_PHONE_NUMBER).getValue();
            String organizationAddress = settingRepository.findByName(Settings.ORGANIZATION_ADDRESS).getValue();
            replaceTag(TemplateTag.CLIENT_SURNAME, message, client.getPerson().getSurname());
            replaceTag(TemplateTag.CLIENT_NAME, message, client.getPerson().getName());
            replaceTag(TemplateTag.CLIENT_PATRONYMIC, message, client.getPerson().getPatronymic());
            replaceTag(TemplateTag.CLIENT_FULL_NAME, message, client.getPerson().getFullName());
            replaceTag(TemplateTag.ORGANIZATION_NAME, message, organizationName);
            replaceTag(TemplateTag.ORGANIZATION_MOBILE_PHONE_NUMBER, message, organizationMobilePhoneNumber);
            replaceTag(TemplateTag.ORGANIZATION_ADDRESS, message, organizationAddress);
        }
    }

    private void replaceTag(TemplateTag tag, Message message, String replacement) {
        String formattedText = StringUtils.replace(message.getText(), propertyReader.readProperty(tag.getValue()),
            replacement);
        message.setText(formattedText);
    }

}
