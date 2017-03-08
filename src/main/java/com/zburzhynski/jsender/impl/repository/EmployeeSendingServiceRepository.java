package com.zburzhynski.jsender.impl.repository;

import static com.zburzhynski.jsender.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jsender.impl.domain.Domain.P_ID;
import static com.zburzhynski.jsender.impl.domain.EmployeeSendingService.P_SENDING_SERVICE;
import static com.zburzhynski.jsender.impl.domain.EmployeeSendingServiceParam.P_SENDING_SERVICE_PARAM;
import static com.zburzhynski.jsender.impl.domain.SendingService.P_SERVICE_PARAMS;
import com.zburzhynski.jsender.api.criteria.SendingServiceSearchCriteria;
import com.zburzhynski.jsender.api.repository.IEmployeeSendingServiceRepository;
import com.zburzhynski.jsender.impl.domain.EmployeeSendingService;
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
 * Implementation of {@link IEmployeeSendingServiceRepository} interface.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Repository
public class EmployeeSendingServiceRepository extends AbstractBaseRepository<String, EmployeeSendingService>
    implements IEmployeeSendingServiceRepository<String, EmployeeSendingService> {

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeSendingService findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_SENDING_SERVICE, P_SENDING_SERVICE);
        criteria.createAlias(P_SERVICE_PARAMS, P_SERVICE_PARAMS, JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias(P_SERVICE_PARAMS + DOT + P_SENDING_SERVICE_PARAM, P_SENDING_SERVICE_PARAM,
            JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        return (EmployeeSendingService) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployeeSendingService> findByCriteria(SendingServiceSearchCriteria searchCriteria,
                                                       Long start, Long end) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_SENDING_SERVICE, P_SENDING_SERVICE);
        CriteriaHelper.addPagination(criteria, start, end);
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(SendingServiceSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends EmployeeSendingService> getDomainClass() {
        return EmployeeSendingService.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
