package com.zburzhynski.jsender.api.domain;

import java.io.Serializable;

/**
 * ISortable interface, for ordering entities in collections.
 * <p/>
 * Date: 19.03.13
 *
 * @author Vladimir Zburzhynski
 */
public interface ISortable extends Serializable {

    /**
     * Gets sort order.
     *
     * @return sort order
     */
    Long getSortOrder();

    /**
     * Sets sort order.
     *
     * @param sortOrder sort order to set
     */
    void setSortOrder(Long sortOrder);

}
