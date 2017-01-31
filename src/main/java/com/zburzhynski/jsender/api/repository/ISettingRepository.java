package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.domain.ISetting;
import com.zburzhynski.jsender.api.domain.SettingCategory;
import com.zburzhynski.jsender.api.domain.Settings;

import java.util.List;

/**
 * Setting repository interface.
 * <p/>
 * Date: 02.08.2016
 *
 * @author Vladimir Zburzhynski
 */
public interface ISettingRepository {

    /**
     * Finds setting by name.
     *
     * @param name {@link Settings} setting name
     * @return setting
     */
    ISetting findByName(Settings name);

    /**
     * Finds settings by category.
     *
     * @param category {@link SettingCategory} setting category
     * @return settings
     */
    List<ISetting> findByCategory(SettingCategory category);

}
