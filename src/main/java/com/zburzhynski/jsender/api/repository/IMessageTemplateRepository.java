package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.criteria.MessageTemplateSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Message template repository.
 * <p/>
 * Date: 21.02.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsou
 */
public interface IMessageTemplateRepository<ID, T extends IDomain> extends IBaseRepository<ID, T> {

    /**
     * Finds message templates by criteria.
     *
     * @param searchCriteria {@link MessageTemplateSearchCriteria} message templates search criteria
     * @param start          start position
     * @param end            end position
     * @return message templates
     */
    List<T> findByCriteria(MessageTemplateSearchCriteria searchCriteria, Long start, Long end);

    /**
     * Counts message templates by criteria.
     *
     * @param searchCriteria {@link MessageTemplateSearchCriteria} message templates search criteria
     * @return message templates count
     */
    int countByCriteria(MessageTemplateSearchCriteria searchCriteria);

}
