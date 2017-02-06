package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.domain.SettingCategory;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.repository.ISettingRepository;
import com.zburzhynski.jsender.api.service.ISettingService;
import com.zburzhynski.jsender.impl.domain.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ISettingService} interface.
 * <p/>
 * Date: 03.08.2016
 *
 * @author Vladimir Zburzhynski
 */
@Service("settingService")
@Transactional(readOnly = true)
public class SettingService implements ISettingService<String, Setting> {

    @Autowired
    private ISettingRepository<String, Setting> settingRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Setting getByName(Settings name) {
        return settingRepository.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Setting> getByCategory(SettingCategory category) {
        return settingRepository.findByCategory(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Setting getById(String id) {
        return settingRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Setting setting) {
        boolean result = false;
        if (setting != null) {
            settingRepository.saveOrUpdate(setting);
            result = true;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

}
