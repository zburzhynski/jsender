package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.ISortable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Contact info email.
 * <p/>
 * Date: 04.02.2017
 *
 * @author Nikita Shevtsov
 */
@Entity
@Table(name = "contact_info_email")
public class ContactInfoEmail extends Domain implements Comparable<ContactInfoEmail>, ISortable {

    public static final String P_ADDRESS = "address";

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "sort_order")
    private Long sortOrder;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(ContactInfoEmail o) {
        return sortOrder.compareTo(o.getSortOrder());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ContactInfoEmail)) {
            return false;
        }

        ContactInfoEmail that = (ContactInfoEmail) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(address, that.address)
            .append(description, that.description)
            .append(sortOrder, that.sortOrder)
            .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(address)
            .append(description)
            .append(sortOrder)
            .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("address", address)
            .append("description", description)
            .append("sortOrder", sortOrder)
            .toString();
    }

}
