package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.criteria.SendingAccountSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Sending account repository interface.
 * <p/>
 * Date: 05.03.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsou
 */
public interface ISendingAccountRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds sending accounts by criteria.
     *
     * @param searchCriteria {@link SendingAccountSearchCriteria} sending service search criteria
     * @param start          start position
     * @param end            end position
     * @return sending accounts
     */
    List<T> findByCriteria(SendingAccountSearchCriteria searchCriteria, Long start, Long end);

    /**
     * Counts sending accounts by criteria.
     *
     * @param searchCriteria {@link SendingAccountSearchCriteria} sending service search criteria
     * @return sending accounts count
     */
    int countByCriteria(SendingAccountSearchCriteria searchCriteria);

}
