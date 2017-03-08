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
 * Employee sending service param.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Entity
@Table(name = "employee_sending_service_param")
public class EmployeeSendingServiceParam extends Domain {

    public static final String P_SENDING_SERVICE_PARAM = "sendingServiceParam";

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sending_service_param_id")
    private SendingServiceParam sendingServiceParam;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SendingServiceParam getSendingServiceParam() {
        return sendingServiceParam;
    }

    public void setSendingServiceParam(SendingServiceParam sendingServiceParam) {
        this.sendingServiceParam = sendingServiceParam;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof EmployeeSendingServiceParam)) {
            return false;
        }

        EmployeeSendingServiceParam that = (EmployeeSendingServiceParam) o;
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
