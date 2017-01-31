package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.domain.ISetting;
import com.zburzhynski.jsender.api.domain.Settings;

/**
 * Setting service interface..
 * <p/>
 * Date: 03.08.2016
 *
 * @author Vladimir Zburzhynski
 */
public interface ISettingService {

    /**
     * Gets setting by name.
     *
     * @param name {@link Settings} setting name
     * @return setting
     */
    ISetting getByName(Settings name);

}
