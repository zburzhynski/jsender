package com.zburzhynski.jsender.impl.rest.helper;

import com.sun.jersey.api.client.ClientResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.ErrorResponse;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AccessDeniedException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AlphanameIncorrectException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.BillingException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.IncorrectArgumentException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.IncorrectPhoneNumberException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.InvalidTokenException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.LimitExceededException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageIdNotFoundException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.NotFoundException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.UndefinedException;

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
     * @throws AlphanameIncorrectException  if alphaname incorrect
     * @throws MessageToLongException       if message to long
     * @throws InvalidTokenException        if token invalid
     */
    public static void throwCreateSmsMessageException(ClientResponse response)
        throws MessageAlreadyExistException, AlphanameIncorrectException,
        MessageToLongException, InvalidTokenException {
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "alphaname is error":
                    throw new AlphanameIncorrectException();
                case "token is invalid":
                    throw new InvalidTokenException();
                default:
            }
        }
    }

    /**
     * Throws check sms message status exception.
     *
     * @param response {@link ClientResponse}
     * @throws IncorrectArgumentException if arguments incorrect
     * @throws NotFoundException          if sms not found
     * @throws InvalidTokenException      if token invalid
     */
    public static void throwCheckSmsMessageStatusException(ClientResponse response)
        throws IncorrectArgumentException, NotFoundException, InvalidTokenException {
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "incorrect arguments":
                    throw new IncorrectArgumentException();
                case "not found":
                    throw new NotFoundException();
                case "token is invalid":
                    throw new InvalidTokenException();
                default:
            }
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
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "undefined error":
                    throw new UndefinedException();
                case "token is invalid":
                    throw new InvalidTokenException();
                default:
            }
        }
    }

    /**
     * Throws send message exception.
     *
     * @param response {@link ClientResponse}
     * @throws IncorrectPhoneNumberException if phone number incorrect
     * @throws IncorrectArgumentException    if arguments incorrect
     * @throws BillingException              if billing error
     * @throws MessageIdNotFoundException    if message id not found
     * @throws AccessDeniedException         if accent denied
     * @throws LimitExceededException        if limit exceeded
     * @throws UndefinedException            if exception undefined
     * @throws InvalidTokenException         if invalid token
     */
    public static void throwSendMessageException(ClientResponse response) throws IncorrectPhoneNumberException,
        IncorrectArgumentException, BillingException, MessageIdNotFoundException,
        AccessDeniedException, LimitExceededException, UndefinedException, InvalidTokenException {
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "incorrect phone number":
                    throw new IncorrectPhoneNumberException();
                case "incorrect arguments":
                    throw new IncorrectArgumentException();
                case "billing error":
                    throw new BillingException();
                case "message_id not found":
                    throw new MessageIdNotFoundException();
                case "access denied":
                    throw new AccessDeniedException();
                case "limit exceeded":
                    throw new LimitExceededException();
                case "undefined error":
                    throw new UndefinedException();
                case "token is invalid":
                    throw new InvalidTokenException();
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
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "undefined error":
                    throw new UndefinedException();
                case "token is invalid":
                    throw new InvalidTokenException();
                default:
            }
        }
    }

    /**
     * Throws check sms exception.
     *
     * @param response {@link ClientResponse}
     * @throws IncorrectArgumentException if arguments incorrect
     * @throws NotFoundException          if sms not found
     * @throws InvalidTokenException      if token invalid
     */
    public static void throwCheckSmsExcepiton(ClientResponse response)
        throws IncorrectArgumentException, NotFoundException, InvalidTokenException {
        if (response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            ErrorResponse errorResponse = response.getEntity(ErrorResponse.class);
            String message = errorResponse.getError();
            switch (message) {
                case "incorrect arguments":
                    throw new IncorrectArgumentException();
                case "not found":
                    throw new NotFoundException();
                case "token is invalid":
                    throw new InvalidTokenException();
                default:
            }
        }
    }

}
