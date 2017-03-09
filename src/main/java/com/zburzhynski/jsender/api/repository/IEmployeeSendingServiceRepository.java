package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.criteria.EmployeeSendingServiceSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Employee sending service repository interface.
 * <p/>
 * Date: 05.03.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsou
 */
public interface IEmployeeSendingServiceRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds sending service by criteria.
     *
     * @param searchCriteria {@link EmployeeSendingServiceSearchCriteria} sending service search criteria
     * @param start          start position
     * @param end            end position
     * @return sending services
     */
    List<T> findByCriteria(EmployeeSendingServiceSearchCriteria searchCriteria, Long start, Long end);

    /**
     * Counts sending service by criteria.
     *
     * @param searchCriteria {@link EmployeeSendingServiceSearchCriteria} sending service search criteria
     * @return sending services count
     */
    int countByCriteria(EmployeeSendingServiceSearchCriteria searchCriteria);

}
