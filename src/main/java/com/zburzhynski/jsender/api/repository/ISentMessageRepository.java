package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.domain.IDomain;

/**
 * Sent message repository interface.
 * <p/>
 * Date: 16.02.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsov
 */
public interface ISentMessageRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {
}
