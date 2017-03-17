package com.zburzhynski.jsender.impl.rest.helper;

import com.sun.jersey.api.client.ClientResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.ErrorResponse;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AlphanameIncorrectException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;

import javax.ws.rs.core.Response;

/**
 * Unisender error helper.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Vladimir Zburzhynski
 */
public class UnisenderErrorHelper {

    /**
     * Throws create sms message method exception.
     *
     * @param response {@link ClientResponse}
     * @throws MessageAlreadyExistException if message already exist
     * @throws AlphanameIncorrectException if alphaname incorrect
     * @throws MessageToLongException if message to long
     */
    public static void throwCreateSmsMessageException(ClientResponse response)
        throws MessageAlreadyExistException, AlphanameIncorrectException, MessageToLongException {
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "alphaname is error":
                    throw new AlphanameIncorrectException();
                default:
            }
        }
    }

}
