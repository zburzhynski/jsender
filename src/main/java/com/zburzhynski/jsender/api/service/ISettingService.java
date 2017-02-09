package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.domain.IDomain;
import com.zburzhynski.jsender.api.domain.SettingCategory;
import com.zburzhynski.jsender.api.domain.Settings;

import java.util.List;

/**
 * Setting service interface.
 * <p/>
 * Date: 03.08.2016
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface ISettingService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets setting by name.
     *
     * @param name {@link Settings} setting name
     * @return setting
     */
    T getByName(Settings name);


    /**
     * Gets settings by category.
     *
     * @param category {@link com.zburzhynski.jsender.api.domain.SettingCategory} setting category
     * @return settings
     */
    List<T> getByCategory(SettingCategory category);

}
