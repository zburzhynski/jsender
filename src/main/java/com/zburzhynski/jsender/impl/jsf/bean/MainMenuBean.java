package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.SETTINGS_VIEW;

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

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Redirects to settings.xhtml page.
     *
     * @return path to redirect
     */
    public String settings() {
        return SETTINGS_VIEW.getPath();
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

}
