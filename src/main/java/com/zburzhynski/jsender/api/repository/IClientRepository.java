package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.criteria.ClientSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Client repository interface.
 * <p/>
 * Date: 08.02.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsov
 */
public interface IClientRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Gets clients by criteria.
     *
     * @param searchCriteria {@link ClientSearchCriteria} client search criteria
     * @return clients
     */
    List<T> findByCriteria(ClientSearchCriteria searchCriteria);

    /**
     * Counts clients by criteria.
     *
     * @param searchCriteria {@link ClientSearchCriteria} client search criteria
     * @return client count
     */
    int countByCriteria(ClientSearchCriteria searchCriteria);

}
