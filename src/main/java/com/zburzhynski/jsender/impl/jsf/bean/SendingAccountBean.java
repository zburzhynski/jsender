package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.SENDING_ACCOUNT;
import static com.zburzhynski.jsender.api.domain.View.SENDING_SERVICE_PARAM;
import static com.zburzhynski.jsender.api.domain.View.SETTINGS;
import com.zburzhynski.jsender.api.criteria.SendingServiceSearchCriteria;
import com.zburzhynski.jsender.api.service.IEmployeeSendingServiceService;
import com.zburzhynski.jsender.api.service.ISendingServiceService;
import com.zburzhynski.jsender.impl.domain.EmployeeSendingService;
import com.zburzhynski.jsender.impl.domain.EmployeeSendingServiceParam;
import com.zburzhynski.jsender.impl.domain.SendingService;
import com.zburzhynski.jsender.impl.domain.SendingServiceParam;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

/**
 * Sending account bean.
 * <p/>
 * Date: 06.03.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@SessionScoped
public class SendingAccountBean {

    private EmployeeSendingService account;

    private EmployeeSendingServiceParam serviceParam;

    private EmployeeSendingServiceParam beforeEditingServiceParam;

    private LazyDataModel<EmployeeSendingService> accountModel;

    @ManagedProperty(value = "#{employeeSendingServiceService}")
    private IEmployeeSendingServiceService<String, EmployeeSendingService> accountService;

    @ManagedProperty(value = "#{sendingServiceService}")
    private ISendingServiceService<String, SendingService> sendingServiceService;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;


    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        accountModel = new LazyDataModel<EmployeeSendingService>() {
            @Override
            public List<EmployeeSendingService> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                                     Map<String, Object> filters) {
                SendingServiceSearchCriteria searchCriteria = new SendingServiceSearchCriteria();
                setRowCount(accountService.countByCriteria(searchCriteria));
                return accountService.getByCriteria(searchCriteria, Long.valueOf(first),
                    Long.valueOf(first + pageSize));
            }
        };
    }

    /**
     * Adds account.
     *
     * @return path for navigating
     */
    public String addAccount() {
        account = new EmployeeSendingService();
        return SENDING_ACCOUNT.getPath();
    }

    /**
     * Edits account.
     *
     * @param id account id
     * @return path for navigating
     */
    public String editAccount(String id) {
        account = accountService.getById(id);
        return SENDING_ACCOUNT.getPath();
    }

    /**
     * Saves account.
     *
     * @return path for navigating
     */
    public String saveAccount() {
        accountService.saveOrUpdate(account);
        return SETTINGS.getPath();
    }

    /**
     * Removes account.
     *
     * @return path for navigating
     */
    public String removeAccount() {
        accountService.delete(account);
        return SETTINGS.getPath();
    }

    /**
     * Edits service param.
     *
     * @return path for navigating
     */
    public String editServiceParam() {
        beforeEditingServiceParam = SerializationUtils.clone(serviceParam);
        return SENDING_SERVICE_PARAM.getPath();
    }

    /**
     * Cancels update service param.
     *
     * @return path for navigation
     */
    public String cancelUpdateServiceParam() {
        serviceParam.setValue(beforeEditingServiceParam.getValue());
        return SENDING_ACCOUNT.getPath();
    }

    /**
     * Sending service change listener.
     *
     * @param event {@link ValueChangeEvent} event
     */
    public void sendingServiceChangeListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            SendingService service = (SendingService) event.getNewValue();
            service = sendingServiceService.getById(service.getId());
            account.setSendingService(service);
            for (SendingServiceParam sendingServiceParam : service.getServiceParams()) {
                EmployeeSendingServiceParam employeeSendingServiceParam = new EmployeeSendingServiceParam();
                employeeSendingServiceParam.setSendingServiceParam(sendingServiceParam);
                employeeSendingServiceParam.setValue(sendingServiceParam.getValue());
                account.getServiceParams().add(employeeSendingServiceParam);
            }
        }
    }

    public Integer getRowCount() {
        return settingBean.getSendingAccountsPerPage();
    }

    public EmployeeSendingService getAccount() {
        return account;
    }

    public void setAccount(EmployeeSendingService account) {
        this.account = account;
    }

    public EmployeeSendingServiceParam getServiceParam() {
        return serviceParam;
    }

    public void setServiceParam(EmployeeSendingServiceParam serviceParam) {
        this.serviceParam = serviceParam;
    }

    public LazyDataModel<EmployeeSendingService> getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(LazyDataModel<EmployeeSendingService> accountModel) {
        this.accountModel = accountModel;
    }

    public void setAccountService(IEmployeeSendingServiceService<String, EmployeeSendingService> accountService) {
        this.accountService = accountService;
    }

    public void setSendingServiceService(ISendingServiceService<String, SendingService> sendingServiceService) {
        this.sendingServiceService = sendingServiceService;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

}
