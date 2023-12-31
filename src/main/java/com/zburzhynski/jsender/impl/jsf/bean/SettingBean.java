package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.SettingCategory.COMMON;
import static com.zburzhynski.jsender.api.domain.SettingCategory.JDENT;
import static com.zburzhynski.jsender.api.domain.SettingCategory.REQUISITE;
import static com.zburzhynski.jsender.api.domain.SettingCategory.VIEW;
import static com.zburzhynski.jsender.api.domain.Settings.CLIENTS_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.DEFAULT_COUNTRY_CODE;
import static com.zburzhynski.jsender.api.domain.Settings.JDENT_INTEGRATION_ENABLED;
import static com.zburzhynski.jsender.api.domain.Settings.JDENT_URL;
import static com.zburzhynski.jsender.api.domain.Settings.MESSAGE_TEMPLATES_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.ORGANIZATION_ADDRESS;
import static com.zburzhynski.jsender.api.domain.Settings.ORGANIZATION_MOBILE_PHONE_NUMBER;
import static com.zburzhynski.jsender.api.domain.Settings.ORGANIZATION_NAME;
import static com.zburzhynski.jsender.api.domain.Settings.SEARCH_RECIPIENTS_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.SENDING_ACCOUNTS_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.SENDING_RECIPIENTS_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.SENT_MESSAGES_PER_PAGE;
import static com.zburzhynski.jsender.api.domain.Settings.SMS_SENDING_DELAY;
import com.zburzhynski.jsender.api.domain.View;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;
import com.zburzhynski.jsender.impl.jsf.validator.SettingValidator;
import org.apache.commons.lang.BooleanUtils;

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

    private Set<Setting> requisiteSettings;

    private Set<Setting> jdentSettings;

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
        requisiteSettings = new TreeSet<>(settingService.getByCategory(REQUISITE));
        jdentSettings = new TreeSet<>(settingService.getByCategory(JDENT));
    }

    /**
     * Edits setting.
     *
     * @param id setting id
     * @return path for navigation
     */
    public String editSetting(String id) {
        setting = (Setting) settingService.getById(id);
        return View.SETTING.getPath();
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

    /**
     * Gets default country code.
     *
     * @return default country code
     */
    public String getDefaultCountryCode() {
        return settings.get(DEFAULT_COUNTRY_CODE.name()).getValue();
    }

    /**
     * Gets sms sending delay.
     *
     * @return sms sending delay
     */
    public long getSmsSendingDelay() {
        return Long.parseLong(settings.get(SMS_SENDING_DELAY.name()).getValue());
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
     * Gets sending recipients per page.
     *
     * @return recipients per page
     */
    public int getSendingRecipientsPerPage() {
        return Integer.parseInt(settings.get(SENDING_RECIPIENTS_PER_PAGE.name()).getValue());
    }

    /**
     * Get search recipients per page.
     *
     * @return search recipients per page
     */
    public int getSearchRecipientsPerPage() {
        return Integer.parseInt(settings.get(SEARCH_RECIPIENTS_PER_PAGE.name()).getValue());
    }

    /**
     * Gets sending accounts per page.
     *
     * @return sending accounts per page
     */
    public int getSendingAccountsPerPage() {
        return Integer.parseInt(settings.get(SENDING_ACCOUNTS_PER_PAGE.name()).getValue());
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
     * Gets organization phone number.
     *
     * @return organization phone number
     */
    public String getOrganizationPhoneNumber() {
        return settings.get(ORGANIZATION_MOBILE_PHONE_NUMBER.name()).getValue();
    }

    /**
     * Is jdent integration enabled.
     *
     * @return true if jdent integration enabled, else false
     */
    public boolean isJdentIntegrationEnabled() {
        return BooleanUtils.toBoolean(settings.get(JDENT_INTEGRATION_ENABLED.name()).getValue());
    }

    /**
     * Jdent integration url.
     *
     * @return jdent integration url
     */
    public String getJdentUrl() {
        return settings.get(JDENT_URL.name()).getValue();
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

    public Set<Setting> getRequisiteSettings() {
        return requisiteSettings;
    }

    public void setRequisiteSettings(Set<Setting> requisiteSettings) {
        this.requisiteSettings = requisiteSettings;
    }

    public Set<Setting> getJdentSettings() {
        return jdentSettings;
    }

    public void setJdentSettings(Set<Setting> jdentSettings) {
        this.jdentSettings = jdentSettings;
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
