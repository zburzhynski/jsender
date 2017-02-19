package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.criteria.SentMessageSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

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

    /**
     * Finds sent messages by criteria.
     *
     * @param searchCriteria {@link SentMessageSearchCriteria} sent message search criteria
     * @return sent messages
     */
    List<T> findByCriteria(SentMessageSearchCriteria searchCriteria);

    /**
     * Counts sent messages by criteria.
     *
     * @param searchCriteria {@link SentMessageSearchCriteria} sent message search criteria
     * @return sent message count
     */
    int countByCriteria(SentMessageSearchCriteria searchCriteria);

}
