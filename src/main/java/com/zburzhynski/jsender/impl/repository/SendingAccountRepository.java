package com.zburzhynski.jsender.impl.repository;

import static com.zburzhynski.jsender.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jsender.impl.domain.Domain.P_ID;
import static com.zburzhynski.jsender.impl.domain.SendingAccount.P_ACCOUNT_PARAMS;
import static com.zburzhynski.jsender.impl.domain.SendingAccount.P_SENDING_SERVICE;
import static com.zburzhynski.jsender.impl.domain.SendingAccountParam.P_SENDING_SERVICE_PARAM;
import static com.zburzhynski.jsender.impl.domain.SendingService.P_SENDING_TYPE;
import com.zburzhynski.jsender.api.criteria.SendingAccountSearchCriteria;
import com.zburzhynski.jsender.api.repository.ISendingAccountRepository;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.util.CriteriaHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link ISendingAccountRepository} interface.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Repository
public class SendingAccountRepository extends AbstractBaseRepository<String, SendingAccount>
    implements ISendingAccountRepository<String, SendingAccount> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SendingAccount findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_SENDING_SERVICE, P_SENDING_SERVICE);
        criteria.createAlias(P_ACCOUNT_PARAMS, P_ACCOUNT_PARAMS, JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias(P_ACCOUNT_PARAMS + DOT + P_SENDING_SERVICE_PARAM, P_SENDING_SERVICE_PARAM,
            JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        return (SendingAccount) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SendingAccount> findByCriteria(SendingAccountSearchCriteria searchCriteria,
                                               Long start, Long end) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_SENDING_SERVICE, P_SENDING_SERVICE);
        if (searchCriteria.getSendingType() != null) {
            criteria.add(Restrictions.eq(P_SENDING_SERVICE + DOT + P_SENDING_TYPE,
                searchCriteria.getSendingType()));
        }
        CriteriaHelper.addPagination(criteria, start, end);
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(SendingAccountSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends SendingAccount> getDomainClass() {
        return SendingAccount.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
