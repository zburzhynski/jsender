package com.zburzhynski.jsender.api.domain;

import java.io.Serializable;
import java.sql.Timestamp;

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

    /**
     * Gets record state.
     *
     * @return record state
     */
    State getState();

    /**
     * Sets record state.
     *
     * @param state record state to set
     */
    void setState(State state);

    /**
     * Gets date of state.
     *
     * @return date of state
     */
    Timestamp getStateDate();

    /**
     * Sets date of state.
     *
     * @param stateDate date of state to set
     */
    void setStateDate(Timestamp stateDate);

}
