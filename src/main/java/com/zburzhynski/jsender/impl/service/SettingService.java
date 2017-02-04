package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.repository.ISettingRepository;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ISettingService} interface.
 * <p/>
 * Date: 03.08.2016
 *
 * @author Vladimir Zburzhynski
 */
@Service("settingService")
public class SettingService implements ISettingService<String, Setting> {

    @Autowired
    private ISettingRepository settingRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Setting getByName(Settings name) {
        return (Setting) settingRepository.findByName(name);
    }

}
