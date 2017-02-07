package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.SettingCategory.EMAIL_SENDING;
import static com.zburzhynski.jsender.api.domain.SettingCategory.SMS_SENDING;
import static com.zburzhynski.jsender.api.domain.Settings.MAIL_PASSWORD;
import static com.zburzhynski.jsender.api.domain.Settings.MAIL_SMTP_HOST;
import static com.zburzhynski.jsender.api.domain.Settings.MAIL_SMTP_PORT;
import static com.zburzhynski.jsender.api.domain.Settings.MAIL_USER_NAME;
import com.zburzhynski.jsender.api.domain.View;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private List<Setting> smsSendingSettings;

    private List<Setting> emailSendingSettings;

    private int tabIndex;

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
            settings.put(item.getName(), item);
        }
        smsSendingSettings = settingService.getByCategory(SMS_SENDING);
        emailSendingSettings = settingService.getByCategory(EMAIL_SENDING);
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
     * Saves setting.
     *
     * @return path for navigating
     */
    public String saveSetting() {
        settingService.saveOrUpdate(setting);
        init();
        return View.SETTINGS_VIEW.getPath();
    }

    public Map<String, Setting> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Setting> settings) {
        this.settings = settings;
    }

    public List<Setting> getSmsSendingSettings() {
        return smsSendingSettings;
    }

    public void setSmsSendingSettings(List<Setting> smsSendingSettings) {
        this.smsSendingSettings = smsSendingSettings;
    }

    public List<Setting> getEmailSendingSettings() {
        return emailSendingSettings;
    }

    public void setEmailSendingSettings(List<Setting> emailSendingSettings) {
        this.emailSendingSettings = emailSendingSettings;
    }

    public ISettingService getSettingService() {
        return settingService;
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

    public void setSettingService(ISettingService settingService) {
        this.settingService = settingService;
    }

}
