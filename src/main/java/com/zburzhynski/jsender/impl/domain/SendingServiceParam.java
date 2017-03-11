package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.ValueType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private ValueType type;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "param_id")
    private Param param;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
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
            .append(name, that.name)
            .append(type, that.type)
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
            .append(name)
            .append(type)
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
            .append("name", name)
            .append("type", type)
            .append("value", value)
            .toString();
    }

}
