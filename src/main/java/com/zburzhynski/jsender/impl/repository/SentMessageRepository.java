package com.zburzhynski.jsender.impl.repository;

import static com.zburzhynski.jsender.impl.domain.SentMessage.P_CLIENT;
import com.zburzhynski.jsender.api.criteria.SentMessageSearchCriteria;
import com.zburzhynski.jsender.api.repository.ISentMessageRepository;
import com.zburzhynski.jsender.impl.domain.SentMessage;
import com.zburzhynski.jsender.impl.util.CriteriaHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link ISentMessageRepository} interface.
 * <p/>
 * Date: 16.02.2017
 *
 * @author Nikita Shevtsov
 */
@Repository
public class SentMessageRepository extends AbstractBaseRepository<String, SentMessage>
    implements ISentMessageRepository<String, SentMessage> {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SentMessage> findByCriteria(SentMessageSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_CLIENT, P_CLIENT);
        CriteriaHelper.addPagination(criteria, searchCriteria.getStart(), searchCriteria.getEnd());
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(SentMessageSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends SentMessage> getDomainClass() {
        return SentMessage.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
