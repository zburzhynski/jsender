package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.CLIENTS;
import static com.zburzhynski.jsender.api.domain.View.SEARCH;
import com.zburzhynski.jsender.api.domain.View;
import com.zburzhynski.jsender.api.rest.client.IPatientRestClient;
import com.zburzhynski.jsender.api.service.IClientService;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.jsf.loader.PatientLazyDataLoader;
import com.zburzhynski.jsender.impl.jsf.validator.ClientSelectValidator;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import org.primefaces.model.LazyDataModel;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Client search bean.
 * <p/>
 * Date: 27.02.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@SessionScoped
public class RecipientBean implements Serializable {

    private static final String SENDING_BEAN = "sendingBean";

    private View redirectFrom = CLIENTS;

    private SearchPatientRequest searchPatientRequest = new SearchPatientRequest();

    private List<Client> selectedClients;

    private LazyDataModel<Client> clientModel;

    @ManagedProperty(value = "#{patientRestClient}")
    private IPatientRestClient patientRestClient;

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
        return SEARCH.getPath();
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

    private void search() {
        clientModel = new PatientLazyDataLoader(patientRestClient, settingBean, searchPatientRequest);
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

    public LazyDataModel<Client> getClientModel() {
        return clientModel;
    }

    public void setClientModel(LazyDataModel<Client> clientModel) {
        this.clientModel = clientModel;
    }

    public void setPatientRestClient(IPatientRestClient patientRestClient) {
        this.patientRestClient = patientRestClient;
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
}
