package com.zburzhynski.jsender.impl.rest.helper;

import com.sun.jersey.api.client.ClientResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.ErrorResponse;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AccessDeniedException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AlphanameIncorrectException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.BillingException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.IncorrectPhoneNumberException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.InvalidTokenException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.LimitExceededException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.ObjectNotFoundException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.UndefinedException;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

/**
 * Unisender error helper.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Vladimir Zburzhynski
 */
public class UnisenderErrorHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnisenderErrorHelper.class);

    private static final String MESSAGE_ID_FIELD = "message_id";

    private static final String ERROR_FIELD = "error";

    /**
     * Throws create sms message method exception.
     *
     * @param response {@link ClientResponse}
     * @throws MessageAlreadyExistException if message already exist
     * @throws AlphanameIncorrectException  if alphaname incorrect
     * @throws MessageToLongException       if message to long
     * @throws InvalidTokenException        if token invalid
     * @throws UndefinedException           if application error occurred
     */
    public static void throwCreateSmsMessageException(ClientResponse response)
        throws MessageAlreadyExistException, AlphanameIncorrectException,
        MessageToLongException, InvalidTokenException, UndefinedException {
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            try {
                JSONObject error = new JSONObject(response.getEntity(String.class));
                if (StringUtils.isNotBlank(error.optString(MESSAGE_ID_FIELD))) {
                    throw new MessageAlreadyExistException();
                }
                switch (error.getString(ERROR_FIELD)) {
                    case "alphaname is error":
                        throw new AlphanameIncorrectException();
                    case "undefined error":
                        throw new UndefinedException();
                    default:
                }
            } catch (JSONException e) {
                LOGGER.error("Error parsing json", e);
            }
        }
    }

    /**
     * Throws check sms message status exception.
     *
     * @param response {@link ClientResponse}
     * @throws ObjectNotFoundException if sms not found
     * @throws InvalidTokenException   if token invalid
     */
    public static void throwCheckSmsMessageStatusException(ClientResponse response)
        throws ObjectNotFoundException, InvalidTokenException {
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new ObjectNotFoundException();
        }
    }

    /**
     * Throws get message list exception.
     *
     * @param response {@link ClientResponse}
     * @throws UndefinedException    if exception undefined
     * @throws InvalidTokenException if token invalid
     */
    public static void throwGetMessageListException(ClientResponse response)
        throws UndefinedException, InvalidTokenException {
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "undefined error":
                    throw new UndefinedException();
                default:
            }
        }
    }

    /**
     * Throws send message exception.
     *
     * @param response {@link ClientResponse}
     * @throws IncorrectPhoneNumberException if phone number incorrect
     * @throws BillingException              if billing error
     * @throws ObjectNotFoundException       if message not found
     * @throws AccessDeniedException         if accent denied
     * @throws LimitExceededException        if limit exceeded
     * @throws UndefinedException            if exception undefined
     * @throws InvalidTokenException         if invalid token
     */
    public static void throwSendMessageException(ClientResponse response) throws IncorrectPhoneNumberException,
        BillingException, ObjectNotFoundException,
        AccessDeniedException, LimitExceededException, UndefinedException, InvalidTokenException {
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new ObjectNotFoundException();
        }
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "incorrect phone number":
                    throw new IncorrectPhoneNumberException();
                case "billing error":
                    throw new BillingException();
                case "access denied":
                    throw new AccessDeniedException();
                case "limit exceeded":
                    throw new LimitExceededException();
                case "undefined error":
                    throw new UndefinedException();
                default:
            }
        }
    }

    /**
     * Throws get limit exception.
     *
     * @param response {@link ClientResponse}
     * @throws UndefinedException    if exception undefined
     * @throws InvalidTokenException if token invalid
     */
    public static void throwGetLimitException(ClientResponse response)
        throws UndefinedException, InvalidTokenException {
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "undefined error":
                    throw new UndefinedException();
                default:
            }
        }
    }

    /**
     * Throws check sms exception.
     *
     * @param response {@link ClientResponse}
     * @throws ObjectNotFoundException if sms not found
     * @throws InvalidTokenException   if token invalid
     */
    public static void throwCheckSmsExcepiton(ClientResponse response)
        throws ObjectNotFoundException, InvalidTokenException {
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new ObjectNotFoundException();
        }
    }

}