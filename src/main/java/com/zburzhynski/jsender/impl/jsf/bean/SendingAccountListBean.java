package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.criteria.SendingAccountSearchCriteria;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class SendingAccountListBean implements Serializable {

    private List<SendingAccount> serviceAccounts;

    @ManagedProperty(value = "#{sendingAccountService}")
    private ISendingAccountService accountService;

    /**
     * Finds accounts by sending type.
     *
     * @param sendingType {@link SendingType} service sending type
     */
    public void findByServiceSendingType(SendingType sendingType) {
        if (sendingType != null) {
            SendingAccountSearchCriteria searchCriteria = new SendingAccountSearchCriteria();
            Set<SendingType> sendingTypes = new HashSet<>();
            sendingTypes.add(sendingType);
            sendingTypes.add(SendingType.EMAIL_SMS);
            searchCriteria.setSendingTypes(sendingTypes);
            serviceAccounts = accountService.getByCriteria(searchCriteria, null, null);
        } else {
            serviceAccounts = new ArrayList<>();
        }
    }

    public List<SendingAccount> getServiceAccounts() {
        return serviceAccounts;
    }

    public void setAccountService(ISendingAccountService<String, SendingAccount> accountService) {
        this.accountService = accountService;
    }

}
