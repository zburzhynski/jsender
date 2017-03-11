package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.SENDING_ACCOUNT;
import static com.zburzhynski.jsender.api.domain.View.SENDING_SERVICE_PARAM;
import static com.zburzhynski.jsender.api.domain.View.SETTINGS;
import com.zburzhynski.jsender.api.criteria.SendingAccountSearchCriteria;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.api.service.ISendingServiceService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import com.zburzhynski.jsender.impl.domain.SendingAccountParam;
import com.zburzhynski.jsender.impl.domain.SendingService;
import com.zburzhynski.jsender.impl.domain.SendingServiceParam;
import com.zburzhynski.jsender.impl.jsf.validator.SendingAccountValidator;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

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

    private SendingAccount account;

    private SendingAccountParam serviceParam;

    private SendingAccountParam beforeEditingServiceParam;

    private LazyDataModel<SendingAccount> accountModel;

    @ManagedProperty(value = "#{sendingAccountService}")
    private ISendingAccountService<String, SendingAccount> accountService;

    @ManagedProperty(value = "#{sendingServiceService}")
    private ISendingServiceService<String, SendingService> sendingServiceService;

    @ManagedProperty(value = "#{sendingAccountValidator}")
    private SendingAccountValidator sendingAccountValidator;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;


    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        accountModel = new LazyDataModel<SendingAccount>() {
            @Override
            public List<SendingAccount> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                             Map<String, Object> filters) {
                SendingAccountSearchCriteria searchCriteria = new SendingAccountSearchCriteria();
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
        account = new SendingAccount();
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
        boolean valid = sendingAccountValidator.validate(account);
        if (valid) {
            accountService.saveOrUpdate(account);
            return SETTINGS.getPath();
        } else {
            return null;
        }
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
     */
    public void sendingServiceChangeListener() {
        if (account.getSendingService() != null) {
            account.getAccountParams().clear();
            account.setSendingService(sendingServiceService.getById(account.getSendingService().getId()));
            for (SendingServiceParam sendingServiceParam : account.getSendingService().getServiceParams()) {
                SendingAccountParam sendingAccountParam = new SendingAccountParam();
                sendingAccountParam.setParam(sendingServiceParam.getParam());
                sendingAccountParam.setValue(sendingServiceParam.getValue());
                account.getAccountParams().add(sendingAccountParam);
            }
        }
    }

    public Integer getRowCount() {
        return settingBean.getSendingAccountsPerPage();
    }

    public SendingAccount getAccount() {
        return account;
    }

    public void setAccount(SendingAccount account) {
        this.account = account;
    }

    public SendingAccountParam getServiceParam() {
        return serviceParam;
    }

    public void setServiceParam(SendingAccountParam serviceParam) {
        this.serviceParam = serviceParam;
    }

    public LazyDataModel<SendingAccount> getAccountModel() {
        return accountModel;
    }

    public void setAccountModel(LazyDataModel<SendingAccount> accountModel) {
        this.accountModel = accountModel;
    }

    public void setAccountService(ISendingAccountService<String, SendingAccount> accountService) {
        this.accountService = accountService;
    }

    public void setSendingServiceService(ISendingServiceService<String, SendingService> sendingServiceService) {
        this.sendingServiceService = sendingServiceService;
    }

    public void setSendingAccountValidator(SendingAccountValidator sendingAccountValidator) {
        this.sendingAccountValidator = sendingAccountValidator;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

}
