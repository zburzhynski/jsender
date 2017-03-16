package com.zburzhynski.jsender.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.zburzhynski.jsender.impl.rest.domain.LimitAmountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

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

    private static final String GET_LIMIT_URL = "http://sms.unisender.by/api/v1/getLimit?token=";

    private Client client = Client.create(new DefaultClientConfig());

    /**
     * Gets sms limit amount.
     *
     * @return sms limit amount
     */
    public int getLimit() {
        try {
            String token = "b374580e1efce9b53c881487275cd3b0";
            WebResource webResource = client.resource(GET_LIMIT_URL + token);
            LimitAmountDto limitAmountDto = webResource.accept(MediaType.APPLICATION_XML).get(LimitAmountDto.class);
            return Integer.valueOf(limitAmountDto.getLimit());
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            LOGGER.error("Get limit amount exception", exception);
            return 0;
        }
    }

}
