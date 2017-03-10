package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.criteria.SendingAccountSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Sending account service interface.
 * <p/>
 * Date: 07.03.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsou
 */
public interface ISendingAccountService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets sending accounts by criteria.
     *
     * @param searchCriteria {@link SendingAccountSearchCriteria} employee sending service search criteria
     * @param start          start position
     * @param end            end position
     * @return sending accounts
     */
    List<T> getByCriteria(SendingAccountSearchCriteria searchCriteria, Long start, Long end);

    /**
     * Counts sending accounts by criteria.
     *
     * @param searchCriteria {@link SendingAccountSearchCriteria} employee sending service search criteria
     * @return sending accounts count
     */
    int countByCriteria(SendingAccountSearchCriteria searchCriteria);

}
