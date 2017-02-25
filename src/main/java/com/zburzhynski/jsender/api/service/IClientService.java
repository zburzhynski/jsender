package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.criteria.ClientSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Client service interface.
 * <p/>
 * Date: 08.02.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsov
 */
public interface IClientService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets clients by criteria.
     *
     * @param searchCriteria {@link ClientSearchCriteria} client search criteria
     * @return clients
     */
    List<T> getByCriteria(ClientSearchCriteria searchCriteria);

    /**
     * Counts clients by criteria.
     *
     * @param searchCriteria {@link ClientSearchCriteria} client search criteria
     * @return clients count
     */
    int countByCriteria(ClientSearchCriteria searchCriteria);


    /**
     * Replicates client.
     *
     * @param client client to replicate
     */
    void replicate(T client);

}
