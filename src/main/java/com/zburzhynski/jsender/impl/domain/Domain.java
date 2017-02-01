package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.IDomain;
import com.zburzhynski.jsender.api.domain.State;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Base entity for other domain objects.
 * <p/>
 * Date: 21.10.12
 *
 * @author Vladimir Zburzhynski
 */
@MappedSuperclass
public class Domain implements IDomain {

    public static final String P_ID = "id";
    public static final String P_STATE = "state";
    public static final String P_STATE_DATE = "stateDate";

    @Transient
    private String uid = UUID.randomUUID().toString();

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "state")
    private State state = State.ADDED;

    @Column(name = "state_date")
    private Timestamp stateDate = new Timestamp(new Date().getTime());

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUid() {
        return uid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getState() {
        return state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(State state) {
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp getStateDate() {
        return stateDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStateDate(Timestamp stateDate) {
        this.stateDate = stateDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Domain)) {
            return false;
        }

        Domain that = (Domain) o;
        return new EqualsBuilder()
            .append(id, that.id)
            .append(state, that.state)
            .append(stateDate, that.stateDate)
            .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .append(state)
            .append(stateDate)
            .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("state", state)
            .append("stateDate", stateDate)
            .toString();
    }

}
