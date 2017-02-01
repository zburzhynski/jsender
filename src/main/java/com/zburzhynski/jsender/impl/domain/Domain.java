package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.IDomain;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
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

    @Transient
    private String uid = UUID.randomUUID().toString();

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

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
            .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .toString();
    }

}
