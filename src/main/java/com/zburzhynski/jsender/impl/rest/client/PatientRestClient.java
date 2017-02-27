package com.zburzhynski.jsender.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jsender.api.rest.client.IPatientRestClient;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jsender.impl.rest.domain.SearchPatientResponse;
import com.zburzhynski.jsender.impl.rest.exception.JdentUnavailableException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

/**
 * Implementation of {@link IPatientRestClient} interface.
 * <p/>
 * Date: 24.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class PatientRestClient implements IPatientRestClient {

    private static final String GET_BY_CRITERIA_URL = "rest/patient/get-by-criteria";

    private Client client = Client.create(new DefaultClientConfig());

    @Override
    public SearchPatientResponse getByCriteria(SearchPatientRequest request, String jdentUrl) {
        try {
            WebResource webResource = client.resource(jdentUrl + GET_BY_CRITERIA_URL);
            return webResource.accept(MediaType.APPLICATION_XML).post(SearchPatientResponse.class, request);
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            return new SearchPatientResponse();
        }
    }

}
