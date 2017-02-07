package com.zburzhynski.jsender.impl.repository;

import com.zburzhynski.jsender.api.domain.IDomain;
import com.zburzhynski.jsender.impl.domain.CriteriaAlias;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

/**
 * Base repository for Entities.
 * <p/>
 * Date: 24.04.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of objects managed by repository.
 * @author Vladimir Zburzhynski
 */
public abstract class AbstractBaseRepository<ID extends Serializable, T extends IDomain> {

    private static final Logger LOGGER = Logger.getLogger(AbstractBaseRepository.class);

    private static final String STATE = "state";

    private static final String DATE_TEMPLATE = "dd.MM.yyyy";

    @Resource
    private SessionFactory sessionFactory;

    /**
     * Inserts object.
     *
     * @param object object to insert.
     * @return generated id
     */
    public ID insert(T object) {
        sessionFactory.getCurrentSession().persist(object);
        return (ID) object.getId();
    }

    /**
     * Replicates object.
     *
     * @param object object to replicate
     */
    public void replicate(T object) {
        sessionFactory.getCurrentSession().replicate(object, ReplicationMode.EXCEPTION);
    }

    /**
     * Finds object by id.
     *
     * @param id object id
     * @return object
     */
    public T findById(ID id) {
        return (T) sessionFactory.getCurrentSession().get(getClassName(), id);
    }

    /**
     * Finds entities from range.
     *
     * @param start   start position
     * @param end     end position
     * @param aliases aliases
     * @param orders  orders
     * @return list of entities
     */
    public List<T> findRange(Long start, Long end, List<CriteriaAlias> aliases, Map<String, Boolean> orders) {
        return findByCriteria(start, end, aliases, null, orders, null, null);
    }

    /**
     * Finds distinct entities from range.
     *
     * @param start   start position
     * @param end     end position
     * @param aliases aliases
     * @param orders  orders
     * @return list of entities
     */
    public List<T> findDistinctRange(Long start, Long end, List<CriteriaAlias> aliases, Map<String, Boolean> orders) {
        return findDistinctByCriteria(start, end, aliases, null, orders, null, null);
    }

    /**
     * Finds all entities in database.
     *
     * @return list of entities
     */
    public List<T> findAll() {
        Criteria criterion = sessionFactory.getCurrentSession().createCriteria(getClassName());
        addDefaultSorting(criterion);
        return criterion.list();
    }

    /**
     * Finds all entities in sorted order.
     *
     * @param aliases aliases
     * @return list of entities
     */
    public List<T> findAll(Map<String, String> aliases) {
        Criteria criterion = sessionFactory.getCurrentSession().createCriteria(getClassName());
        if (MapUtils.isNotEmpty(aliases)) {
            for (Map.Entry<String, String> entry : aliases.entrySet()) {
                criterion.createAlias(entry.getKey(), entry.getValue());
            }
        }
        addDefaultSorting(criterion);
        return criterion.list();
    }

    /**
     * Finds entities by criteria.
     *
     * @param start       start position
     * @param end         end position
     * @param aliases     aliases
     * @param projections projections
     * @param orders      orders
     * @param fetchModes  fetch modes
     * @param criteria    criteria
     * @return list of entities
     */
    public List<T> findByCriteria(Long start, Long end, List<CriteriaAlias> aliases,
                                  ProjectionList projections, Map<String, Boolean> orders,
                                  Map<String, FetchMode> fetchModes, List<Criterion> criteria) {
        Criteria criterion = buildCriteria(start, end, aliases, projections, orders, fetchModes, criteria);
        return criterion.list();
    }

    /**
     * Finds distinct entities by criteria.
     *
     * @param start       start position
     * @param end         end position
     * @param aliases     aliases
     * @param projections projections
     * @param orders      orders
     * @param fetchModes  fetch modes
     * @param criteria    criteria
     * @return list of entities
     */
    public List<T> findDistinctByCriteria(Long start, Long end, List<CriteriaAlias> aliases,
                                          ProjectionList projections, Map<String, Boolean> orders,
                                          Map<String, FetchMode> fetchModes, List<Criterion> criteria) {
        Criteria criterion = buildCriteria(start, end, aliases, projections, orders, fetchModes, criteria);
        return criterion.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * Finds unique entity by criteria.
     *
     * @param start       start position
     * @param end         end position
     * @param aliases     aliases
     * @param projections projections
     * @param orders      orders
     * @param fetchModes  fetch modes
     * @param criteria    criteria
     * @return unique entity
     */
    public T findUniqueByCriteria(Long start, Long end, List<CriteriaAlias> aliases,
                                  ProjectionList projections, Map<String, Boolean> orders,
                                  Map<String, FetchMode> fetchModes, List<Criterion> criteria) {
        Criteria criterion = buildCriteria(start, end, aliases, projections, orders, fetchModes, criteria);
        return (T) criterion.uniqueResult();
    }

    /**
     * Finds count by criteria.
     *
     * @param aliases     aliases
     * @param projections projections
     * @param orders      orders
     * @param fetchModes  fetch modes
     * @param criteria    criteria
     * @return count
     */
    public Integer countByCriteria(List<CriteriaAlias> aliases,
                                   ProjectionList projections, Map<String, Boolean> orders,
                                   Map<String, FetchMode> fetchModes, List<Criterion> criteria) {
        Criteria criterion = buildCriteria(null, null, aliases, projections, orders, fetchModes, criteria);
        Object uniqueResult = criterion.uniqueResult();
        return uniqueResult == null ? 0 : ((Number) uniqueResult).intValue();
    }

    /**
     * Updates object.
     *
     * @param object object to update
     */
    public void update(T object) {
        sessionFactory.getCurrentSession().merge(object);
    }

    /**
     * Saves or updates the object.
     *
     * @param object object to save or update
     */
    public void saveOrUpdate(T object) {
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    /**
     * Deletes object.
     *
     * @param object object to delete
     */
    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }

    /**
     * Gets total record count.
     *
     * @return total record count
     */
    public Integer getTotalCount() {
        ProjectionList projections = Projections.projectionList();
        projections.add(Projections.rowCount());
        return countByCriteria(null, projections, null, null, null);
    }

    /**
     * Invokes query.
     *
     * @param hql        query
     * @param parameters query parameters
     * @return list of result
     */
    public List<T> invokeQuery(String hql, Map<String, Object> parameters) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (entry.getValue() instanceof Collection) {
                query.setParameterList(entry.getKey(), (Collection) entry.getValue());
            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.list();
    }

    /**
     * Gets hibernate session factory.
     *
     * @return hibernate session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Gets current session.
     *
     * @return current session
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Gets domain object class.
     *
     * @return domain object class
     */
    protected abstract Class<? extends T> getDomainClass();

    /**
     * Gets default sorting.
     *
     * @return default sorting
     */
    protected abstract Map<String, Boolean> getDefaultSorting();

    /**
     * Gets domain class name.
     *
     * @return domain class name
     */
    protected String getClassName() {
        return getDomainClass().getName();
    }

    /**
     * Builds criteria.
     *
     * @param start       start position
     * @param end         end position
     * @param aliases     aliases
     * @param projections projections
     * @param orders      orders
     * @param fetchModes  fetch modes
     * @param criteria    criteria
     * @return
     */
    private Criteria buildCriteria(Long start, Long end, List<CriteriaAlias> aliases,
                                   ProjectionList projections, Map<String, Boolean> orders,
                                   Map<String, FetchMode> fetchModes, List<Criterion> criteria) {
        Criteria criterion = sessionFactory.getCurrentSession().createCriteria(getClassName());
        if (CollectionUtils.isNotEmpty(aliases)) {
            for (CriteriaAlias alias : aliases) {
                criterion.createAlias(alias.getAssociationPath(), alias.getAlias(), alias.getJoinType());
            }
        }
        if (CollectionUtils.isNotEmpty(criteria)) {
            for (Criterion c : criteria) {
                criterion.add(c);
            }
        }
        if (MapUtils.isNotEmpty(fetchModes)) {
            for (String key : fetchModes.keySet()) {
                criterion.setFetchMode(key, fetchModes.get(key));
            }
        }
        if (projections != null) {
            criterion.setProjection(projections);
        }
        if (MapUtils.isNotEmpty(orders)) {
            for (String key : orders.keySet()) {
                criterion.addOrder(orders.get(key) ? Order.asc(key) : Order.desc(key));
            }
        }
        if (start != null && end != null) {
            criterion.setMaxResults(end.intValue() - start.intValue()).setFirstResult(start.intValue());
        }
        return criterion;
    }


    /**
     * Adds default sorting to criteria.
     *
     * @param criterion criterion to add sorting
     */
    private void addDefaultSorting(Criteria criterion) {
        Map<String, Boolean> defaultSorting = getDefaultSorting();
        if (MapUtils.isNotEmpty(defaultSorting)) {
            for (String key : defaultSorting.keySet()) {
                criterion.addOrder(defaultSorting.get(key) ? Order.asc(key) : Order.desc(key));
            }
        }
    }

}
