package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.criteria.SentMessageSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Sent message service interface.
 * <p/>
 * Date: 16.02.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsov
 */
public interface ISentMessageService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets sent messages by criteria.
     *
     * @param searchCriteria {@link SentMessageSearchCriteria} sent message search criteria
     * @return sent messages
     */
    List<T> getByCriteria(SentMessageSearchCriteria searchCriteria);

    /**
     * Counts sent messages by criteria.
     *
     * @param searchCriteria {@link SentMessageSearchCriteria} sent message search criteria
     * @return sent message count
     */
    int countByCriteria(SentMessageSearchCriteria searchCriteria);

}
