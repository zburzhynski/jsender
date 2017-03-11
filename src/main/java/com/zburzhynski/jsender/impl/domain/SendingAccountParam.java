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
 * Sending account param.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Entity
@Table(name = "sending_account_param")
public class SendingAccountParam extends Domain {

    public static final String P_SENDING_SERVICE_PARAM = "sendingServiceParam";

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

        if (!(o instanceof SendingAccountParam)) {
            return false;
        }

        SendingAccountParam that = (SendingAccountParam) o;
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
