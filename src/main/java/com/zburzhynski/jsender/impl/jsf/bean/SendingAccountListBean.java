package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.criteria.EmployeeSendingServiceSearchCriteria;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.service.IEmployeeSendingServiceService;
import com.zburzhynski.jsender.impl.domain.EmployeeSendingService;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Sending account list bean.
 * <p/>
 * Date: 09.03.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@SessionScoped
public class SendingAccountListBean {

    private List<EmployeeSendingService> serviceAccounts;

    @ManagedProperty(value = "#{employeeSendingServiceService}")
    private IEmployeeSendingServiceService<String, EmployeeSendingService> accountService;

    /**
     * Finds accounts by sending type.
     *
     * @param sendingType {@link SendingType} service sending type
     */
    public void findByServiceSendingType(SendingType sendingType) {
        EmployeeSendingServiceSearchCriteria searchCriteria = new EmployeeSendingServiceSearchCriteria();
        searchCriteria.setSendingType(sendingType);
        serviceAccounts = accountService.getByCriteria(searchCriteria, null, null);
    }

    public List<EmployeeSendingService> getServiceAccounts() {
        return serviceAccounts;
    }

    public void setServiceAccounts(List<EmployeeSendingService> serviceAccounts) {
        this.serviceAccounts = serviceAccounts;
    }

    public void setAccountService(IEmployeeSendingServiceService<String, EmployeeSendingService> accountService) {
        this.accountService = accountService;
    }

}
