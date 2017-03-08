package com.zburzhynski.jsender.impl.repository;

import static com.zburzhynski.jsender.impl.domain.Domain.P_ID;
import static com.zburzhynski.jsender.impl.domain.SendingService.P_SERVICE_PARAMS;
import com.zburzhynski.jsender.api.repository.ISendingServiceRepository;
import com.zburzhynski.jsender.impl.domain.SendingService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link ISendingServiceRepository} interface.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Repository
public class SendingServiceRepository extends AbstractBaseRepository<String, SendingService>
    implements ISendingServiceRepository<String, SendingService> {

    /**
     * {@inheritDoc}
     */
    @Override
    public SendingService findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_SERVICE_PARAMS, P_SERVICE_PARAMS, JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        return (SendingService) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends SendingService> getDomainClass() {
        return SendingService.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
