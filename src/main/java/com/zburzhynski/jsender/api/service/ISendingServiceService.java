package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.domain.IDomain;

/**
 * Sending service service interface.
 * <p/>
 * Date: 07.03.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsou
 */
public interface ISendingServiceService<ID, T extends IDomain> extends IBaseService<ID, T> {
}
