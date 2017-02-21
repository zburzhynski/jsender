package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.criteria.MessageTemplateSearchCriteria;
import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Message template service interface.
 * <p/>
 * Date: 21.02.2017
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of model object.
 * @author Nikita Shevtsou
 */
public interface IMessageTemplateService<ID, T extends IDomain> extends IBaseService<ID, T> {

    /**
     * Gets message templates by criteria.
     *
     * @param searchCriteria {@link MessageTemplateSearchCriteria} message templates search criteria
     * @param start          start position
     * @param end            end position
     * @return message templates
     */
    List<T> getByCriteria(MessageTemplateSearchCriteria searchCriteria, Long start, Long end);

    /**
     * Counts message templates by criteria.
     *
     * @param searchCriteria {@link MessageTemplateSearchCriteria} message templates search criteria
     * @return message templates count
     */
    int countByCriteria(MessageTemplateSearchCriteria searchCriteria);

}
