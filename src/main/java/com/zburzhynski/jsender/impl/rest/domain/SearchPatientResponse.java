package com.zburzhynski.jsender.impl.rest.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Search patient response.
 * <p/>
 * Date: 5/22/2015
 *
 * @author Vladimir Zburzhynski
 */
@XmlRootElement
public class SearchPatientResponse implements Serializable {

    private List<PatientDto> patients = new ArrayList<>();

    private int totalCount;

    /**
     * Adds patient to response.
     *
     * @param patient patient to add
     */
    public void addPatient(PatientDto patient) {
        patients.add(patient);
    }

    public List<PatientDto> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientDto> patients) {
        this.patients = patients;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
