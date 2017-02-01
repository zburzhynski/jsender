package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.domain.ISetting;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.repository.ISettingRepository;
import com.zburzhynski.jsender.api.service.ISettingService;
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
public class SettingService implements ISettingService {

    @Autowired
    private ISettingRepository settingRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public ISetting getByName(Settings name) {
        return settingRepository.findByName(name);
    }

}
