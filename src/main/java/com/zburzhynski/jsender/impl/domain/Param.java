package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.ValueType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Param.
 * <p/>
 * Date: 11.03.2017
 *
 * @author Nikita Shevtsou
 */
@Entity
@Table(name = "param")
public class Param extends Domain {

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private ValueType type;

    @Column(name = "description")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Param)) {
            return false;
        }

        Param that = (Param) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(name, that.name)
            .append(type, that.type)
            .append(description, that.description)
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
            .append(description)
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
            .append("description", description)
            .toString();
    }

}
