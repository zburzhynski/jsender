package com.zburzhynski.jsender.impl.jsf.loader;

import com.zburzhynski.jsender.api.domain.Gender;
import com.zburzhynski.jsender.api.rest.client.IPatientRestClient;
import com.zburzhynski.jsender.impl.domain.Client;
import com.zburzhynski.jsender.impl.domain.ContactInfo;
import com.zburzhynski.jsender.impl.domain.ContactInfoEmail;
import com.zburzhynski.jsender.impl.domain.ContactInfoPhone;
import com.zburzhynski.jsender.impl.domain.Person;
import com.zburzhynski.jsender.impl.jsf.bean.SettingBean;
import com.zburzhynski.jsender.impl.rest.domain.ContactInfoDto;
import com.zburzhynski.jsender.impl.rest.domain.ContactInfoEmailDto;
import com.zburzhynski.jsender.impl.rest.domain.ContactInfoPhoneDto;
import com.zburzhynski.jsender.impl.rest.domain.PatientDto;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientResponse;
import org.apache.commons.collections.CollectionUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.UUID;

/**
 * Patient lazy data loader.
 * <p/>
 * Date: 02.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public class PatientLazyDataLoader extends LazyDataModel<Client> {

    private IPatientRestClient patientRestClient;

    private SettingBean settingBean;

    private SearchPatientRequest searchRequest;

    /**
     * Constructor.
     *
     * @param patientRestClient {@link com.zburzhynski.jsender.impl.rest.client.PatientRestClient}
     * @param settingBean       {@link com.zburzhynski.jsender.impl.jsf.bean.SettingBean}
     * @param searchRequest     {@link com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest}
     */
    public PatientLazyDataLoader(IPatientRestClient patientRestClient, SettingBean settingBean,
                                 SearchPatientRequest searchRequest) {
        if (patientRestClient == null || settingBean == null || searchRequest == null) {
            throw new IllegalArgumentException();
        }
        this.patientRestClient = patientRestClient;
        this.settingBean = settingBean;
        this.searchRequest = searchRequest;
    }

    @Override
    public List<Client> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                             Map<String, Object> filters) {
        searchRequest.setStart(Long.valueOf(first));
        searchRequest.setEnd(Long.valueOf(first + pageSize));
        SearchPatientResponse response = patientRestClient.getByCriteria(searchRequest, settingBean.getJdentUrl());
        setRowCount(response.getTotalCount());
        return parseClients(response.getPatients());
    }

    @Override
    public Client getRowData(String rowKey) {
        List<Client> wrapped = (List<Client>) getWrappedData();
        if (CollectionUtils.isNotEmpty(wrapped)) {
            for (Client selected : wrapped) {
                if (selected.getId().equals(rowKey)) {
                    return selected;
                }
            }
        }
        return null;
    }

    private List<Client> parseClients(List<PatientDto> patients) {
        List<Client> clients = new ArrayList<>();
        for (PatientDto patientDto : patients) {
            Client clientSrc = new Client();
            clientSrc.setId(patientDto.getId());
            clientSrc.setPerson(parsePerson(patientDto));
            clientSrc.setContactInfo(parseContactInfo(patientDto.getContactInfo()));
            clients.add(clientSrc);
        }
        return clients;
    }

    private Person parsePerson(PatientDto patientDto) {
        Person personSrc = new Person();
        personSrc.setId(UUID.randomUUID().toString());
        personSrc.setName(patientDto.getName());
        personSrc.setSurname(patientDto.getSurname());
        personSrc.setPatronymic(patientDto.getPatronymic());
        personSrc.setBirthday(patientDto.getBirthday());
        personSrc.setGender(patientDto.getGender().equals(Gender.M.name())
            ? Gender.M : Gender.F);
        return personSrc;
    }

    private ContactInfo parseContactInfo(ContactInfoDto contactInfoDto) {
        ContactInfo contactInfoSrc = new ContactInfo();
        SortedSet<ContactInfoPhone> phoneSrcList = contactInfoSrc.getPhones();
        if (CollectionUtils.isNotEmpty(contactInfoDto.getPhones())) {
            for (ContactInfoPhoneDto phoneDto : contactInfoDto.getPhones()) {
                ContactInfoPhone phoneSrc = new ContactInfoPhone();
                phoneSrc.setCountryCode(phoneDto.getCountryCode());
                phoneSrc.setCityCode(phoneDto.getCityCode());
                phoneSrc.setPhoneNumber(phoneDto.getPhoneNumber());
                phoneSrc.setMobileOperator(phoneDto.getMobileOperator());
                phoneSrc.setDescription(phoneDto.getDescription());
                phoneSrc.setSortOrder(phoneDto.getSortOrder());
                phoneSrcList.add(phoneSrc);
            }
        }
        contactInfoSrc.setPhones(phoneSrcList);
        SortedSet<ContactInfoEmail> emailSrcList = contactInfoSrc.getEmails();
        if (CollectionUtils.isNotEmpty(contactInfoDto.getEmails())) {
            for (ContactInfoEmailDto emailDto : contactInfoDto.getEmails()) {
                ContactInfoEmail emailSrc = new ContactInfoEmail();
                emailSrc.setAddress(emailDto.getAddress());
                emailSrc.setDescription(emailDto.getDescription());
                emailSrc.setSortOrder(emailDto.getSortOrder());
                emailSrcList.add(emailSrc);
            }
        }
        contactInfoSrc.setEmails(emailSrcList);
        return contactInfoSrc;
    }

}
