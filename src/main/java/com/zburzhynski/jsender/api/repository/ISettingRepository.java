package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.domain.IDomain;
import com.zburzhynski.jsender.api.domain.SettingCategory;
import com.zburzhynski.jsender.api.domain.Settings;

import java.util.List;

/**
 * Setting repository interface.
 * <p/>
 * Date: 02.08.2016
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Vladimir Zburzhynski
 */
public interface ISettingRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds setting by name.
     *
     * @param name {@link Settings} setting name
     * @return setting
     */
    T findByName(Settings name);

    /**
     * Finds settings by category.
     *
     * @param category {@link SettingCategory} setting category
     * @return settings
     */
    List<T> findByCategory(SettingCategory category);

}
