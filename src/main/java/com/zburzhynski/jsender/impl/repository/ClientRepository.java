package com.zburzhynski.jsender.impl.repository;

import static com.zburzhynski.jsender.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jsender.impl.domain.Client.P_CONTACT_INFO;
import static com.zburzhynski.jsender.impl.domain.Client.P_PERSON;
import static com.zburzhynski.jsender.impl.domain.ContactInfo.P_EMAILS;
import static com.zburzhynski.jsender.impl.domain.ContactInfo.P_PHONES;
import static com.zburzhynski.jsender.impl.domain.Domain.P_ID;
import static com.zburzhynski.jsender.impl.domain.Person.P_NAME;
import static com.zburzhynski.jsender.impl.domain.Person.P_PATRONYMIC;
import static com.zburzhynski.jsender.impl.domain.Person.P_SURNAME;
import com.zburzhynski.jsender.api.criteria.ClientSearchCriteria;
import com.zburzhynski.jsender.api.repository.IClientRepository;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.util.CriteriaHelper;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IClientRepository} interface.
 * <p/>
 * Date: 08.02.2017
 *
 * @author Nikita Shevtsov
 */
@Repository
public class ClientRepository extends AbstractBaseRepository<String, Client>
    implements IClientRepository<String, Client> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Client findById(String id) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_PERSON, P_PERSON);
        criteria.createAlias(P_CONTACT_INFO, P_CONTACT_INFO);
        criteria.createAlias(P_CONTACT_INFO + DOT + P_EMAILS, P_EMAILS, JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias(P_CONTACT_INFO + DOT + P_PHONES, P_PHONES, JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq(P_ID, id));
        return (Client) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findByCriteria(ClientSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.createAlias(P_PERSON, P_PERSON);
        CriteriaHelper.addPagination(criteria, searchCriteria.getStart(), searchCriteria.getEnd());
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_SURNAME));
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_NAME));
        criteria.addOrder(Order.asc(P_PERSON + DOT + P_PATRONYMIC));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(ClientSearchCriteria searchCriteria) {
        Criteria criteria = getSession().createCriteria(getDomainClass());
        criteria.setProjection(Projections.rowCount());
        Object uniqueResult = criteria.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends Client> getDomainClass() {
        return Client.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
