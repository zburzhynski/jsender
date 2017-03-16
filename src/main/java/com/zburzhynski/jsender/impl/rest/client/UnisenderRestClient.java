package com.zburzhynski.jsender.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.ErrorResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.GetLimitResponse;
import com.zburzhynski.jsender.impl.rest.exception.unisender.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Unisender.by rest client.
 * <p/>
 * Date: 16.03.2017
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class UnisenderRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnisenderRestClient.class);

    private static final String GET_LIMIT_URL = "http://sms.unisender.by/api/v1/getLimit?token=%s";

    private static final String CHECK_SMS_URL = "http://sms.unisender.by/api/v1/checkSMS?token=%s&sms_id=%s";

    private Client client = Client.create(new DefaultClientConfig());

    /**
     * Gets sms limit amount.
     *
     * @param token token
     * @return {@link GetLimitResponse}
     */
    public GetLimitResponse getLimit(String token) {
        try {
            String url = String.format(GET_LIMIT_URL, token);
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(GetLimitResponse.class);
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            LOGGER.error("Exception", exception);
            return null;
        }
    }

    /**
     * Checks sms delivery status.
     *
     * @param token token
     * @param smsId unique identifier of sms
     * @return {@link CheckSmsResponse}
     * @throws NotFoundException if sms not found
     */
    public CheckSmsResponse checkSms(String token, int smsId) throws NotFoundException {
        try {
            String url = String.format(CHECK_SMS_URL, token, smsId);
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(CheckSmsResponse.class);
        } catch (UniformInterfaceException exception) {
            ClientResponse response = exception.getResponse();
            if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                throw new NotFoundException();
            }
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            LOGGER.error("Exception", exception);
            return null;
        } catch (ClientHandlerException exception) {
            LOGGER.error("Exception", exception);
            return null;
        }
    }

}
