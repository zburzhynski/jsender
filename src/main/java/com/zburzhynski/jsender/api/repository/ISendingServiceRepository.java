package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.domain.IDomain;

/**
 * Sending service repository interface.
 * <p/>
 * Date: 05.03.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsou
 */
public interface ISendingServiceRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {
}
