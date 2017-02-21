package com.zburzhynski.jsender.impl.repository;

import com.zburzhynski.jsender.api.criteria.MessageTemplateSearchCriteria;
import com.zburzhynski.jsender.api.repository.IMessageTemplateRepository;
import com.zburzhynski.jsender.impl.domain.MessageTemplate;
import com.zburzhynski.jsender.impl.util.CriteriaHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IMessageTemplateRepository} interface.
 * <p/>
 * Date: 21.02.2017
 *
 * @author Nikita Shevtsou
 */
@Repository
public class MessageTemplateRepository extends AbstractBaseRepository<String, MessageTemplate>
    implements IMessageTemplateRepository<String, MessageTemplate> {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MessageTemplate> findByCriteria(MessageTemplateSearchCriteria searchCriteria, Long start, Long end) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        CriteriaHelper.addPagination(criteria, start, end);
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(MessageTemplateSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends MessageTemplate> getDomainClass() {
        return MessageTemplate.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
