package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.RECIPIENTS;
import static com.zburzhynski.jsender.api.domain.View.SENDING;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import com.zburzhynski.jsender.api.domain.PhoneNumberType;
import com.zburzhynski.jsender.api.domain.RecipientSourceType;
import com.zburzhynski.jsender.api.domain.View;
import com.zburzhynski.jsender.api.dto.Recipient;
import com.zburzhynski.jsender.api.rest.client.IPatientRestClient;
import com.zburzhynski.jsender.impl.jsf.loader.PatientLazyDataLoader;
import com.zburzhynski.jsender.impl.rest.domain.ContactInfoDto;
import com.zburzhynski.jsender.impl.rest.domain.ContactInfoEmailDto;
import com.zburzhynski.jsender.impl.rest.domain.ContactInfoPhoneDto;
import com.zburzhynski.jsender.impl.rest.domain.PatientDto;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jsender.impl.util.BeanUtils;
import com.zburzhynski.jsender.impl.util.MessageHelper;
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

    private List<PatientDto> selectedRecipients;

    private LazyDataModel<PatientDto> recipientModel;

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
     * Select recipients.
     *
     * @return path for navigation
     */
    public String selectRecipient() {
        if (isEmpty(selectedRecipients)) {
            messageHelper.addMessage(RECIPIENT_NOT_SELECTED);
            return null;
        }
        selectToSendingForm();
        return redirectFrom.getPath();
    }

    /**
     * Cancel select recipients.
     *
     * @return path for navigation
     */
    public String cancelSelectRecipient() {
        return redirectFrom.getPath();
    }

    private void search() {
        recipientModel = new PatientLazyDataLoader(patientRestClient, settingBean, searchPatientRequest);
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

    public List<PatientDto> getSelectedRecipients() {
        return selectedRecipients;
    }

    public void setSelectedRecipients(List<PatientDto> selectedRecipients) {
        this.selectedRecipients = selectedRecipients;
    }

    public LazyDataModel<PatientDto> getRecipientModel() {
        return recipientModel;
    }

    public void setRecipientModel(LazyDataModel<PatientDto> recipientModel) {
        this.recipientModel = recipientModel;
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
                for (PatientDto selected : selectedRecipients) {
                    ContactInfoDto contactInfo = selected.getContactInfo();
                    if (isEmpty(contactInfo.getEmails()) && !hasMobilePhone(contactInfo.getPhones())) {
                        continue;
                    }
                    Recipient recipient = new Recipient();
                    recipient.setId(selected.getId());
                    recipient.setSurname(selected.getSurname());
                    recipient.setName(selected.getName());
                    recipient.setPatronymic(selected.getPatronymic());
                    recipient.setRecipientSource(RecipientSourceType.JDENT);
                    for (ContactInfoPhoneDto phone : contactInfo.getPhones()) {
                        recipient.addPhone(phone.getFullNumber());
                    }
                    for (ContactInfoEmailDto email : contactInfo.getEmails()) {
                        recipient.addEmail(email.getAddress());
                    }
                    sendingBean.getMessageToSend().addRecipient(recipient);
                }
            }
        }
    }

    private boolean hasMobilePhone(List<ContactInfoPhoneDto> phones) {
        for (ContactInfoPhoneDto phone : phones) {
            if (PhoneNumberType.MOBILE.equals(phone.getPhoneNumberType())) {
                return true;
            }
        }
        return false;
    }

}
