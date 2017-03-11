package com.zburzhynski.jsender.impl.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Sending service param.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Entity
@Table(name = "sending_service_param")
public class SendingServiceParam extends Domain {

    public static final String P_PARAM = "param";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "param_id")
    private Param param;

    @Column(name = "value")
    private String value;

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SendingServiceParam)) {
            return false;
        }

        SendingServiceParam that = (SendingServiceParam) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(value, that.value)
            .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(value)
            .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("value", value)
            .toString();
    }

}
