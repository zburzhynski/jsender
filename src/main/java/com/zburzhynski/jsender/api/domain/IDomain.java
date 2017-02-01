package com.zburzhynski.jsender.api.domain;

import java.io.Serializable;

/**
 * Domain interface.
 * <p/>
 * Date: 21.10.12
 *
 * @author Vladimir Zburzhynski
 */
public interface IDomain extends Serializable {

    /**
     * Gets uid of entity instance.
     *
     * @return uid of entity instance
     */
    String getUid();

    /**
     * Sets uid of entity instance.
     *
     * @param uid uid of entity instance to set
     */
    void setUid(String uid);

    /**
     * Gets unique identifier of entity.
     *
     * @return unique identifier of entity
     */
    String getId();

    /**
     * Sets unique identifier of entity.
     *
     * @param id unique identifier of entity to set
     */
    void setId(String id);
}
