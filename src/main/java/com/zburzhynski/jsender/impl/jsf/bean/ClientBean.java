package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.CLIENT;
import static com.zburzhynski.jsender.api.domain.View.CLIENTS;
import static com.zburzhynski.jsender.api.domain.View.CONTACT_INFO_EMAIL;
import static com.zburzhynski.jsender.api.domain.View.CONTACT_INFO_PHONE;
import static com.zburzhynski.jsender.api.domain.View.SENDING;
import com.zburzhynski.jsender.api.criteria.ClientSearchCriteria;
import com.zburzhynski.jsender.api.domain.Gender;
import com.zburzhynski.jsender.api.domain.PhoneNumberType;
import com.zburzhynski.jsender.api.domain.View;
import com.zburzhynski.jsender.api.rest.client.IPatientRestClient;
import com.zburzhynski.jsender.api.service.IClientService;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.domain.ContactInfoEmail;
import com.zburzhynski.jsender.impl.domain.ContactInfoPhone;
import com.zburzhynski.jsender.impl.domain.Person;
import com.zburzhynski.jsender.impl.jsf.loader.PatientLazyDataLoader;
import com.zburzhynski.jsender.impl.jsf.validator.ClientContactInfoValidator;
import com.zburzhynski.jsender.impl.jsf.validator.ClientSelectValidator;
import com.zburzhynski.jsender.impl.rest.domain.PatientDto;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientResponse;
import com.zburzhynski.jsender.impl.rest.exception.JdentUnavailableException;
import com.zburzhynski.jsender.impl.util.BeanUtils;
import com.zburzhynski.jsender.impl.util.MessageHelper;
import com.zburzhynski.jsender.impl.util.SortableUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Client bean.
 * <p/>
 * Date: 08.02.2017
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@SessionScoped
public class ClientBean implements Serializable {

    private static final String SENDING_BEAN = "sendingBean";

    private View redirectFrom = CLIENTS;

    private boolean emailAdd;

    private boolean phoneAdd;

    private SearchPatientRequest searchPatientRequest = new SearchPatientRequest();

    private List<Client> selectedClients;

    private ContactInfoEmail beforeEditingEmail;

    private ContactInfoEmail email;

    private ContactInfoPhone beforeEditingPhone;

    private ContactInfoPhone phone;

    private Client client;

    private LazyDataModel<Client> clientModel;

    private List<Client> datasource;

    @ManagedProperty(value = "#{patientRestClient}")
    private IPatientRestClient patientRestClient;

    @ManagedProperty(value = "#{clientContactInfoValidator}")
    private ClientContactInfoValidator clientContactInfoValidator;

    @ManagedProperty(value = "#{clientSelectValidator}")
    private ClientSelectValidator clientSelectValidator;

    @ManagedProperty(value = "#{clientService}")
    private IClientService<String, Client> clientService;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        searchPatientRequest = new SearchPatientRequest();
        search();
    }

    /**
     * Searches patient by criteria.
     *
     * @return path for navigation
     */
    public String searchClient() {
        search();
        return CLIENTS.getPath();
    }

    /**
     * Cancels client search.
     */
    public void cancelSearchClient() {
        searchPatientRequest = new SearchPatientRequest();
        search();
    }

    /**
     * Clears  patient search.
     */
    public void clearSearchFilter() {
        searchPatientRequest = new SearchPatientRequest();
    }

    public void search() {
        if (settingBean.isJdentIntegrationEnabled()) {
            clientModel = new PatientLazyDataLoader(patientRestClient, settingBean, searchPatientRequest);
        } else {
            clientModel = new ClientDataModel();
        }
    }

    /**
     * Adds client.
     *
     * @return path for navigating
     */

    public String addClient() {
        client = new Client();
        return CLIENT.getPath();
    }

    /**
     * Edits client.
     *
     * @param id client id
     * @return path for navigating
     */
    public String editClient(String id) {
        client = clientService.getById(id);
        return CLIENT.getPath();
    }

    /**
     * Saves client.
     *
     * @return path for navigating
     */
    public String saveClient() {
        clientService.saveOrUpdate(client);
        return CLIENTS.getPath();
    }

    /**
     * Removes client.
     *
     * @return path for navigating
     */
    public String removeClient() {
        clientService.delete(client);
        return CLIENTS.getPath();
    }

    /**
     * Adds phone.
     *
     * @return path for navigating
     */
    public String addPhone() {
        phoneAdd = true;
        phone = new ContactInfoPhone();
        phone.setCountryCode(settingBean.getDefaultCountryCode());
        phone.setPhoneNumberType(PhoneNumberType.MOBILE);
        Long sortOrder = SortableUtils.nextSortOrder(client.getContactInfo().getPhones());
        phone.setSortOrder(sortOrder);
        return CONTACT_INFO_PHONE.getPath();
    }

    /**
     * Edits phone.
     *
     * @return path for navigating
     */
    public String editPhone() {
        phoneAdd = false;
        beforeEditingPhone = SerializationUtils.clone(phone);
        return CONTACT_INFO_PHONE.getPath();
    }

    /**
     * Saves phone.
     *
     * @return path for navigating
     */
    public String savePhone() {
        boolean valid = clientContactInfoValidator.validatePhone(client.getContactInfo(), phone);
        if (!valid) {
            return null;
        }
        if (phoneAdd) {
            client.getContactInfo().getPhones().add(phone);
        }
        return CLIENT.getPath();
    }

    /**
     * Cancels update email.
     *
     * @return path for navigation
     */
    public String cancelUpdatePhone() {
        if (!phoneAdd) {
            phone.setCountryCode(beforeEditingPhone.getCountryCode());
            phone.setCityCode(beforeEditingPhone.getCityCode());
            phone.setPhoneNumber(beforeEditingPhone.getPhoneNumber());
            phone.setPhoneNumberType(beforeEditingPhone.getPhoneNumberType());
            phone.setMobileOperator(beforeEditingPhone.getMobileOperator());
            phone.setDescription(beforeEditingPhone.getDescription());
        }
        return CLIENT.getPath();
    }

    /**
     * Removes phone.
     *
     * @return path for navigating
     */
    public String removePhone() {
        client.getContactInfo().getPhones().remove(phone);
        return CLIENT.getPath();
    }

    /**
     * Select clients.
     *
     * @return path for navigation
     */
    public String selectClient() {
        boolean valid = clientSelectValidator.validate(selectedClients);
        if (!valid) {
            return null;
        }
        selectToSendingForm();
        return redirectFrom.getPath();
    }

    /**
     * Cancel select client.
     *
     * @return path for navigation
     */
    public String cancelSelectClient() {
        return redirectFrom.getPath();
    }

    /**
     * Check that phone is valid.
     *
     * @param infoPhone phone
     * @return true if valid, else false
     */
    public boolean isValidPhone(ContactInfoPhone infoPhone) {
        return infoPhone.getCountryCode() != null
            && infoPhone.getCityCode() != null
            && infoPhone.getPhoneNumber() != null;
    }

    /**
     * Adds email.
     *
     * @return path for navigating
     */
    public String addEmail() {
        emailAdd = true;
        email = new ContactInfoEmail();
        email.setSortOrder(SortableUtils.nextSortOrder(client.getContactInfo().getEmails()));
        return CONTACT_INFO_EMAIL.getPath();
    }

    /**
     * Edits email.
     *
     * @return path for navigating
     */
    public String editEmail() {
        emailAdd = false;
        beforeEditingEmail = SerializationUtils.clone(email);
        return CONTACT_INFO_EMAIL.getPath();
    }

    /**
     * Saves email.
     *
     * @return path for navigating
     */
    public String saveEmail() {
        boolean valid = clientContactInfoValidator.validateEmail(client.getContactInfo(), email);
        if (!valid) {
            return null;
        }
        if (emailAdd) {
            client.getContactInfo().getEmails().add(email);
        }
        return CLIENT.getPath();
    }

    /**
     * Cancels update email.
     *
     * @return path for navigation
     */
    public String cancelUpdateEmail() {
        if (!emailAdd) {
            email.setAddress(beforeEditingEmail.getAddress());
            email.setDescription(beforeEditingEmail.getDescription());
        }
        return CLIENT.getPath();
    }

    /**
     * Removes email.
     *
     * @return path for navigating
     */
    public String removeEmail() {
        client.getContactInfo().getEmails().remove(email);
        return CLIENT.getPath();
    }

    public View getRedirectFrom() {
        return redirectFrom;
    }

    public void setRedirectFrom(View redirectFrom) {
        this.redirectFrom = redirectFrom;
    }

    public SearchPatientRequest getSearchPatientRequest() {
        return searchPatientRequest;
    }

    public void setSearchPatientRequest(SearchPatientRequest searchPatientRequest) {
        this.searchPatientRequest = searchPatientRequest;
    }

    public List<Client> getSelectedClients() {
        return selectedClients;
    }

    public void setSelectedClients(List<Client> selectedClients) {
        this.selectedClients = selectedClients;
    }

    public ContactInfoEmail getEmail() {
        return email;
    }

    public void setEmail(ContactInfoEmail email) {
        this.email = email;
    }

    public ContactInfoPhone getPhone() {
        return phone;
    }

    public void setPhone(ContactInfoPhone phone) {
        this.phone = phone;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LazyDataModel<Client> getClientModel() {
        return clientModel;
    }

    public void setClientModel(LazyDataModel<Client> clientModel) {
        this.clientModel = clientModel;
    }

    public Integer getRowCount() {
        return settingBean.getClientsPerPage();
    }

    public void setPatientRestClient(IPatientRestClient patientRestClient) {
        this.patientRestClient = patientRestClient;
    }

    public void setClientContactInfoValidator(ClientContactInfoValidator clientContactInfoValidator) {
        this.clientContactInfoValidator = clientContactInfoValidator;
    }

    public void setClientSelectValidator(ClientSelectValidator clientSelectValidator) {
        this.clientSelectValidator = clientSelectValidator;
    }

    public void setClientService(IClientService<String, Client> clientService) {
        this.clientService = clientService;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

    private void selectToSendingForm() {
        if (SENDING.equals(redirectFrom)) {
            SendingBean sendingBean = BeanUtils.getSessionBean(SENDING_BEAN);
            if (sendingBean != null) {
                for (Client object : selectedClients) {
                    object = clientService.getById(object.getId());
                    if (!sendingBean.getRecipients().contains(object)) {
                        sendingBean.getRecipients().add(object);
                    }
                }
            }
        }
    }

    private class ClientDataModel extends LazyDataModel<Client> {

        /**
         * {@inheritDoc}
         */
        @Override
        public List<Client> load(int first, int pageSize, String sortField,
                                 SortOrder sortOrder, Map<String, Object> filters) {
            ClientSearchCriteria searchCriteria = new ClientSearchCriteria();
            searchCriteria.setStart(Long.valueOf(first));
            searchCriteria.setEnd(Long.valueOf(first + pageSize));
            datasource = clientService.getByCriteria(searchCriteria);
            int count = clientService.countByCriteria(searchCriteria);
            setRowCount(count);
            return datasource;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Client getRowData(String rowKey) {
            for (Client object : datasource) {
                if (object.getId().equals(rowKey)) {
                    return object;
                }
            }
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getRowKey(Client object) {
            return object.getId();
        }

    }

}
