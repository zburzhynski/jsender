package com.zburzhynski.jsender.impl.jsf.loader;

import com.zburzhynski.jsender.api.rest.client.IPatientRestClient;
import com.zburzhynski.jsender.impl.jsf.bean.SettingBean;
import com.zburzhynski.jsender.impl.rest.domain.PatientDto;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientResponse;
import org.apache.commons.collections.CollectionUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * Patient lazy data loader.
 * <p/>
 * Date: 02.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public class PatientLazyDataLoader extends LazyDataModel<PatientDto> {

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
    public List<PatientDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                 Map<String, Object> filters) {
        searchRequest.setStart(Long.valueOf(first));
        searchRequest.setEnd(Long.valueOf(first + pageSize));
        SearchPatientResponse response = patientRestClient.getByCriteria(searchRequest, settingBean.getJdentUrl());
        setRowCount(response.getTotalCount());
        return response.getPatients();
    }

    @Override
    public PatientDto getRowData(String rowKey) {
        List<PatientDto> wrapped = (List<PatientDto>) getWrappedData();
        if (CollectionUtils.isNotEmpty(wrapped)) {
            for (PatientDto selected : wrapped) {
                if (selected.getId().equals(rowKey)) {
                    return selected;
                }
            }
        }
        return null;
    }

}
