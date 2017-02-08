package com.zburzhynski.jsender.impl.util;

import org.hibernate.Criteria;

/**
 * Criteria helper.
 * <p/>
 * Date: 03.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public class CriteriaHelper {

    private CriteriaHelper() {
        new AssertionError();
    }

    /**
     * Adds pagination to criteria.
     *
     * @param criteria {@link org.hibernate.Criteria} criteria
     * @param start    start position
     * @param end      end position
     */
    public static void addPagination(Criteria criteria, Long start, Long end) {
        if (start != null && end != null) {
            criteria.setMaxResults(end.intValue() - start.intValue()).setFirstResult(start.intValue());
        }
    }

}
