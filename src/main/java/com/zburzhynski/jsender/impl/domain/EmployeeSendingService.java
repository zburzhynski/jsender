package com.zburzhynski.jsender.impl.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Employee sending service.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Entity
@Table(name = "employee_sending_service")
public class EmployeeSendingService extends Domain {

    public static final String P_SENDING_SERVICE = "sendingService";

    public static final String P_SERVICE_PARAMS = "serviceParams";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sending_service_id")
    private SendingService sendingService;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employee_sending_service_id", nullable = false)
    private List<EmployeeSendingServiceParam> serviceParams;

    public SendingService getSendingService() {
        return sendingService;
    }

    public void setSendingService(SendingService sendingService) {
        this.sendingService = sendingService;
    }

    /**
     * Gets service params.
     *
     * @return service params
     */
    public List<EmployeeSendingServiceParam> getServiceParams() {
        if (serviceParams == null) {
            serviceParams = new ArrayList<>();
        }
        return serviceParams;
    }

    public void setServiceParams(List<EmployeeSendingServiceParam> serviceParams) {
        this.serviceParams = serviceParams;
    }

}
