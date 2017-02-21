package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.SettingCategory.COMMON;
import static com.zburzhynski.jsender.api.domain.SettingCategory.EMAIL_SENDING;
import static com.zburzhynski.jsender.api.domain.SettingCategory.REQUISITE;
import static com.zburzhynski.jsender.api.domain.SettingCategory.SMS_SENDING;
import static com.zburzhynski.jsender.api.domain.SettingCategory.VIEW;
import static com.zburzhynski.jsender.api.domain.Settings.CLIENTS_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.DEFAULT_COUNTRY_CODE;
import static com.zburzhynski.jsender.api.domain.Settings.MAIL_PASSWORD;
import static com.zburzhynski.jsender.api.domain.Settings.MAIL_SMTP_HOST;
import static com.zburzhynski.jsender.api.domain.Settings.MAIL_SMTP_PORT;
import static com.zburzhynski.jsender.api.domain.Settings.MAIL_USER_NAME;
import static com.zburzhynski.jsender.api.domain.Settings.MESSAGE_TEMPLATES_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.ORGANIZATION_ADDRESS;
import static com.zburzhynski.jsender.api.domain.Settings.ORGANIZATION_NAME;
import static com.zburzhynski.jsender.api.domain.Settings.RECIPIENTS_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.SENT_MESSAGES_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.SMS_PASSWORD;
import static com.zburzhynski.jsender.api.domain.Settings.SMS_USER_NAME;
import com.zburzhynski.jsender.api.domain.View;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;
import com.zburzhynski.jsender.impl.jsf.validator.SettingValidator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 * Application configuration bean.
 * <p/>
 * Date: 05.02.2017
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@ApplicationScoped
public class SettingBean implements Serializable {

    private Setting setting;

    private Map<String, Setting> settings;

    private Set<Setting> commonSettings;

    private Set<Setting> viewSettings;

    private Set<Setting> smsSendingSettings;

    private Set<Setting> emailSendingSettings;

    private Set<Setting> requisiteSettings;

    private int tabIndex;

    @ManagedProperty(value = "#{settingValidator}")
    private SettingValidator settingValidator;

    @ManagedProperty(value = "#{settingService}")
    private ISettingService settingService;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        settings = new HashMap<>();
        List<Setting> all = settingService.getAll();
        for (Setting item : all) {
            settings.put(item.getName().toUpperCase(), item);
        }
        commonSettings = new TreeSet<>(settingService.getByCategory(COMMON));
        viewSettings = new TreeSet<>(settingService.getByCategory(VIEW));
        smsSendingSettings = new TreeSet<>(settingService.getByCategory(SMS_SENDING));
        emailSendingSettings = new TreeSet<>(settingService.getByCategory(EMAIL_SENDING));
        requisiteSettings = new TreeSet<>(settingService.getByCategory(REQUISITE));
    }

    /**
     * Gets default country code.
     *
     * @return default country code
     */
    public String getDefaultCountryCode() {
        return settings.get(DEFAULT_COUNTRY_CODE.name()).getValue();
    }

    /**
     * Gets clients per page.
     *
     * @return clients per page
     */
    public int getClientsPerPage() {
        return Integer.parseInt(settings.get(CLIENTS_PER_PAGE.name()).getValue());
    }

    /**
     * Gets recipients per page.
     *
     * @return recipients per page
     */
    public int getRecipientsPerPage() {
        return Integer.parseInt(settings.get(RECIPIENTS_PER_PAGE.name()).getValue());
    }

    /**
     * Gets sent messages per page.
     *
     * @return sent messages per page
     */
    public int getSentMessagesPerPage() {
        return Integer.parseInt(settings.get(SENT_MESSAGES_PER_PAGE.name()).getValue());
    }

    /**
     * Gets message templates per page.
     *
     * @return message templates per page
     */
    public int getMessageTemplatesPerPage() {
        return Integer.parseInt(settings.get(MESSAGE_TEMPLATES_PER_PAGE.name()).getValue());
    }

    /**
     * Gets sms user name.
     *
     * @return sms user name
     */
    public String getSMSUserName() {
        return settings.get(SMS_USER_NAME.name()).getValue();
    }

    /**
     * Gets sms password.
     *
     * @return sms password
     */
    public String getSMSPassword() {
        return settings.get(SMS_PASSWORD.name()).getValue();
    }

    /**
     * Gets mail smtp host.
     *
     * @return mail smtp host
     */
    public String getMailSmtpHost() {
        return settings.get(MAIL_SMTP_HOST.name()).getValue();
    }

    /**
     * Gets mail smtp port.
     *
     * @return mail smtp port
     */
    public String getMailSmtpPort() {
        return settings.get(MAIL_SMTP_PORT.name()).getValue();
    }

    /**
     * Gets mail user name.
     *
     * @return mail user name
     */
    public String getMailUserName() {
        return settings.get(MAIL_USER_NAME.name()).getValue();
    }

    /**
     * Gets mail password.
     *
     * @return mail password
     */
    public String getMailPassword() {
        return settings.get(MAIL_PASSWORD.name()).getValue();
    }

    /**
     * Gets organization name.
     *
     * @return organization name
     */
    public String getOrganizationName() {
        return settings.get(ORGANIZATION_NAME.name()).getValue();
    }

    /**
     * Gets organization address.
     *
     * @return organization address
     */
    public String getOrganizationAddress() {
        return settings.get(ORGANIZATION_ADDRESS.name()).getValue();
    }

    /**
     * Saves setting.
     *
     * @return path for navigating
     */
    public String saveSetting() {
        boolean valid = settingValidator.validate(setting);
        if (!valid) {
            return null;
        }
        settingService.saveOrUpdate(setting);
        init();
        return View.SETTINGS.getPath();
    }

    public Map<String, Setting> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Setting> settings) {
        this.settings = settings;
    }

    public Set<Setting> getCommonSettings() {
        return commonSettings;
    }

    public void setCommonSettings(Set<Setting> commonSettings) {
        this.commonSettings = commonSettings;
    }

    public Set<Setting> getViewSettings() {
        return viewSettings;
    }

    public void setViewSettings(Set<Setting> viewSettings) {
        this.viewSettings = viewSettings;
    }

    public Set<Setting> getSmsSendingSettings() {
        return smsSendingSettings;
    }

    public void setSmsSendingSettings(Set<Setting> smsSendingSettings) {
        this.smsSendingSettings = smsSendingSettings;
    }

    public Set<Setting> getEmailSendingSettings() {
        return emailSendingSettings;
    }

    public void setEmailSendingSettings(Set<Setting> emailSendingSettings) {
        this.emailSendingSettings = emailSendingSettings;
    }

    public Set<Setting> getRequisiteSettings() {
        return requisiteSettings;
    }

    public void setRequisiteSettings(Set<Setting> requisiteSettings) {
        this.requisiteSettings = requisiteSettings;
    }

    public ISettingService getSettingService() {
        return settingService;
    }

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public void setSettingValidator(SettingValidator settingValidator) {
        this.settingValidator = settingValidator;
    }

}
