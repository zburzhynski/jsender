package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.criteria.SendingServiceSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Employee sending service service interface.
 * <p/>
 * Date: 07.03.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsou
 */
public interface IEmployeeSendingServiceService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets sending service by criteria.
     *
     * @param searchCriteria {@link SendingServiceSearchCriteria} sending service search criteria
     * @param start          start position
     * @param end            end position
     * @return sending services
     */
    List<T> getByCriteria(SendingServiceSearchCriteria searchCriteria, Long start, Long end);

    /**
     * Counts sending service by criteria.
     *
     * @param searchCriteria {@link SendingServiceSearchCriteria} sending service search criteria
     * @return sending services count
     */
    int countByCriteria(SendingServiceSearchCriteria searchCriteria);

}
