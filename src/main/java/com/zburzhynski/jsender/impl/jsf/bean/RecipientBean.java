package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.RECIPIENTS;
import static com.zburzhynski.jsender.api.domain.View.SENDING;
import com.zburzhynski.jsender.api.domain.View;
import com.zburzhynski.jsender.api.rest.client.IPatientRestClient;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.jsf.loader.PatientLazyDataLoader;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jsender.impl.util.BeanUtils;
import com.zburzhynski.jsender.impl.util.MessageHelper;
import org.apache.commons.collections.CollectionUtils;
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

    private static final String RECIPIENT_NOT_SELECTED = "recipientSearch.recipientNotSelected";

    private View redirectFrom = RECIPIENTS;

    private SearchPatientRequest searchPatientRequest = new SearchPatientRequest();

    private List<Client> selectedClients;

    private LazyDataModel<Client> clientModel;

    @ManagedProperty(value = "#{patientRestClient}")
    private IPatientRestClient patientRestClient;

    @ManagedProperty(value = "#{messageHelper}")
    private MessageHelper messageHelper;

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
     * Searches recipient by criteria.
     *
     * @return path for navigation
     */
    public String searchRecipient() {
        search();
        return RECIPIENTS.getPath();
    }

    /**
     * Cancels client search.
     */
    public void cancelSearchRecipient() {
        searchPatientRequest = new SearchPatientRequest();
        search();
    }

    /**
     * Clears  patient search.
     */
    public void clearSearchFilter() {
        searchPatientRequest = new SearchPatientRequest();
    }

    /**
     * Select clients.
     *
     * @return path for navigation
     */
    public String selectClient() {
        if (CollectionUtils.isEmpty(selectedClients)) {
            messageHelper.addMessage(RECIPIENT_NOT_SELECTED);
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

    private void search() {
        clientModel = new PatientLazyDataLoader(patientRestClient, settingBean, searchPatientRequest);
    }

    public Integer getRowCount() {
        return settingBean.getSearchRecipientsPerPage();
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

    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

    private void selectToSendingForm() {
        if (SENDING.equals(redirectFrom)) {
            SendingBean sendingBean = BeanUtils.getSessionBean(SENDING_BEAN);
            if (sendingBean != null) {
                for (Client object : selectedClients) {
                    if (!sendingBean.getRecipients().contains(object)) {
                        sendingBean.getRecipients().add(object);
                    }
                }
            }
        }
    }

}
