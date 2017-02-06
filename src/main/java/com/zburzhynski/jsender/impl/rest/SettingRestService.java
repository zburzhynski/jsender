package com.zburzhynski.jsender.impl.rest;

import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Setting rest service.
 * <p/>
 * Date: 02.02.2017
 *
 * @author Nikita Shevtsov
 */
@Path("/settings")
@Component
public class SettingRestService {

    @Resource
    private ISettingService settingService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Setting getSetting() {
        settingService.getByName(Settings.MAIL_PASSWORD);
        Setting setting = new Setting();
        setting.setName("settinasdf");
        return setting;
    }

}
