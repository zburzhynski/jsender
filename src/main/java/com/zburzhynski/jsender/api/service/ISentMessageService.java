package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.domain.IDomain;

/**
 * Sent message service interface.
 * <p/>
 * Date: 16.02.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsov
 */
public interface ISentMessageService<ID, T extends IDomain> extends IBaseService<ID, T> {
}
