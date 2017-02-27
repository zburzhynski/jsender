package com.zburzhynski.jsender.api.rest.client;

import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientResponse;

/**
 * Patient rest client interface.
 * <p/>
 * Date: 24.05.15
 *
 * @author Vladimir Zburzhynski
 */
public interface IPatientRestClient {

    /**
     * Gets patient by criteria.
     *
     * @param request  {@link SearchPatientRequest} search patient request
     * @param jdentUrl jdent url
     * @return {@link SearchPatientResponse} search patient response
     */
    SearchPatientResponse getByCriteria(SearchPatientRequest request, String jdentUrl);

}
