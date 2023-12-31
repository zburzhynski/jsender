package com.zburzhynski.jsender.impl.rest.helper;

import com.sun.jersey.api.client.ClientResponse;
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
public final class UnisenderErrorHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnisenderErrorHelper.class);

    private static final String MESSAGE_ID_FIELD = "message_id";

    private static final String ERROR_FIELD = "error";

    private UnisenderErrorHelper() {
        throw new AssertionError();
    }

    /**
     * Throws create sms message method exception.
     *
     * @param response {@link ClientResponse}
     * @throws MessageAlreadyExistException if message already exist
     * @throws AlphanameIncorrectException  if alphaname incorrect
     * @throws MessageToLongException       if message to long
     * @throws InvalidTokenException        if token invalid
     * @throws UndefinedException           if exception undefined
     */
    public static void throwCreateSmsMessageException(ClientResponse response)
        throws MessageAlreadyExistException, AlphanameIncorrectException,
        MessageToLongException, InvalidTokenException, UndefinedException {
        LOGGER.debug("Client response {}", response);
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            try {
                String message = response.getEntity(String.class);
                LOGGER.debug("Response message {}", message);
                JSONObject error = new JSONObject(message);
                int messageId = error.optInt(MESSAGE_ID_FIELD);
                if (messageId != 0) {
                    throw new MessageAlreadyExistException(messageId);
                }
                if ("alphaname is error".equals(error.getString(ERROR_FIELD))) {
                    throw new AlphanameIncorrectException();
                }
            } catch (JSONException exception) {
                LOGGER.error("Error parsing json", exception);
            }
        }
        throw new UndefinedException();
    }

    /**
     * Throws check sms message status exception.
     *
     * @param response {@link ClientResponse}
     * @throws ObjectNotFoundException if sms not found
     * @throws InvalidTokenException   if token invalid
     * @throws UndefinedException      if exception undefined
     */
    public static void throwCheckSmsMessageStatusException(ClientResponse response)
        throws ObjectNotFoundException, InvalidTokenException, UndefinedException {
        LOGGER.debug("Client response {}", response);
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new ObjectNotFoundException();
        }
        throw new UndefinedException();
    }

    /**
     * Throws get message list exception.
     *
     * @param response {@link ClientResponse}
     * @throws InvalidTokenException if token invalid
     * @throws UndefinedException    if exception undefined
     */
    public static void throwGetMessageListException(ClientResponse response)
        throws UndefinedException, InvalidTokenException {
        LOGGER.debug("Client response {}", response);
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        throw new UndefinedException();
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
     * @throws InvalidTokenException         if invalid token
     * @throws UndefinedException            if exception undefined
     */
    public static void throwSendMessageException(ClientResponse response) throws IncorrectPhoneNumberException,
        BillingException, ObjectNotFoundException,
        AccessDeniedException, LimitExceededException, InvalidTokenException, UndefinedException {
        LOGGER.debug("Client response {}", response);
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            try {
                String message = response.getEntity(String.class);
                LOGGER.debug("Response message {}", message);
                JSONObject error = new JSONObject(message);
                switch (error.getString(ERROR_FIELD)) {
                    case "token is invalid":
                        throw new InvalidTokenException();
                    case "limit excided":
                        throw new LimitExceededException();
                    default:
                }
            } catch (JSONException exception) {
                LOGGER.error("Error parsing json", exception);
            }
        }
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new ObjectNotFoundException();
        }
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            try {
                String message = response.getEntity(String.class);
                LOGGER.debug("Response message {}", message);
                JSONObject error = new JSONObject(message);
                switch (error.getString(ERROR_FIELD)) {
                    case "incorrect phone number":
                        throw new IncorrectPhoneNumberException();
                    case "billing error":
                        throw new BillingException();
                    case "access denied":
                        throw new AccessDeniedException();
                    default:
                }
            } catch (JSONException exception) {
                LOGGER.error("Error parsing json", exception);
            }
        }
        throw new UndefinedException();
    }

    /**
     * Throws get limit exception.
     *
     * @param response {@link ClientResponse}
     * @throws InvalidTokenException if token invalid
     * @throws UndefinedException    if exception undefined
     */
    public static void throwGetLimitException(ClientResponse response)
        throws InvalidTokenException, UndefinedException {
        LOGGER.debug("Client response {}", response);
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        throw new UndefinedException();
    }

    /**
     * Throws check sms exception.
     *
     * @param response {@link ClientResponse}
     * @throws ObjectNotFoundException if sms not found
     * @throws InvalidTokenException   if token invalid
     * @throws UndefinedException      if exception undefined
     */
    public static void throwCheckSmsExcepiton(ClientResponse response)
        throws ObjectNotFoundException, InvalidTokenException, UndefinedException {
        LOGGER.debug("Client response {}", response);
        if (response.getStatus() == Response.Status.FORBIDDEN.getStatusCode()) {
            throw new InvalidTokenException();
        }
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new ObjectNotFoundException();
        }
        throw new UndefinedException();
    }

}
