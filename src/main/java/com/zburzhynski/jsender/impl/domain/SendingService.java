package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.SendingType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Sending service.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Entity
@Table(name = "sending_service")
public class SendingService extends Domain {

    public static final String P_NAME = "name";

    public static final String P_SERVICE_PARAMS = "serviceParams";

    @Column(name = "name")
    private String name;

    @Column(name = "sending_type")
    private SendingType sendingType;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sending_service_id", nullable = false)
    private List<SendingServiceParam> serviceParams = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SendingType getSendingType() {
        return sendingType;
    }

    public void setSendingType(SendingType sendingType) {
        this.sendingType = sendingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SendingServiceParam> getServiceParams() {
        return serviceParams;
    }

    public void setServiceParams(List<SendingServiceParam> serviceParams) {
        this.serviceParams = serviceParams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SendingService)) {
            return false;
        }

        SendingService that = (SendingService) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(name, that.name)
            .append(sendingType, that.sendingType)
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
            .append(sendingType)
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
            .append("sendingType", sendingType)
            .append("description", description)
            .toString();
    }

}
