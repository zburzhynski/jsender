package com.zburzhynski.jsender.impl.rest.client;

import static com.zburzhynski.jsender.api.domain.CommonConstant.AMPERSAND;
import static com.zburzhynski.jsender.api.domain.CommonConstant.EQUALS;
import static java.net.URLEncoder.encode;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.zburzhynski.jsender.impl.rest.domain.unisender.BaseUnisenderRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsMessageStatusRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsMessageStatusResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CheckSmsResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CreateSmsMessageRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.CreateSmsMessageResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.GetLimitRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.GetLimitResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.GetMessageListResponse;
import com.zburzhynski.jsender.impl.rest.domain.unisender.SendSmsRequest;
import com.zburzhynski.jsender.impl.rest.domain.unisender.SendSmsResponse;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AccessDeniedException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.AlphanameIncorrectException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.BillingException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.HostUnavailableException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.IncorrectPhoneNumberException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.InvalidTokenException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.LimitExceededException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.ObjectNotFoundException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.UndefinedException;
import com.zburzhynski.jsender.impl.rest.helper.UnisenderErrorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import javax.annotation.PostConstruct;
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

    private static final String CREATE_SMS_MESSAGE_URL =
        "http://sms.unisender.by/api/v1/createSmsMessage?token=%s&message=%s";

    private static final String CHECK_SMS_MESSAGE_STATUS_URL =
        "http://sms.unisender.by/api/v1/checkSMSMessageStatus?token=%s&message_id=%s";

    private static final String GET_MESSAGE_LIST_URL = "http://sms.unisender.by/api/v1/getMessagesList?token=%s";

    private static final String SEND_SMS_URL = "http://sms.unisender.by/api/v1/sendSms?token=%s&message_id=%s&phone=%s";

    private static final String GET_LIMIT_URL = "http://sms.unisender.by/api/v1/getLimit?token=%s";

    private static final String CHECK_SMS_URL = "http://sms.unisender.by/api/v1/checkSMS?token=%s&sms_id=%s";

    private static final String ALPHANAME_ID_PARAM = "alphaname_id";

    public static final String UTF_8 = "UTF-8";

    private Client client;

    /**
     * Inits.
     */
    @PostConstruct
    public void init() {
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        client = Client.create(config);
    }

    /**
     * Creates sms message.
     *
     * @param request {@link CreateSmsMessageRequest} request
     * @return {@link CreateSmsMessageResponse} response
     * @throws MessageAlreadyExistException if message already exist
     * @throws AlphanameIncorrectException  if alphaname incorrect
     * @throws MessageToLongException       if message to long
     * @throws InvalidTokenException        if token invalid
     * @throws HostUnavailableException if host unavailable
     * @throws UndefinedException           if exception undefined
     */
    public CreateSmsMessageResponse createSmsMessage(CreateSmsMessageRequest request)
        throws MessageAlreadyExistException, AlphanameIncorrectException, MessageToLongException,
        InvalidTokenException, HostUnavailableException, UndefinedException {
        try {
            String url = String.format(CREATE_SMS_MESSAGE_URL, request.getToken(), encode(request.getMessage(), UTF_8));
            if (request.getAlphanameId() != null) {
                String alphanameUrl = AMPERSAND + ALPHANAME_ID_PARAM + EQUALS + request.getAlphanameId();
                url += alphanameUrl;
            }
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(new GenericType<List<CreateSmsMessageResponse>>() {
            }).get(0);
        } catch (UniformInterfaceException exception) {
            UnisenderErrorHelper.throwCreateSmsMessageException(exception.getResponse());
        } catch (ClientHandlerException exception) {
            LOGGER.error("Client handler exception occurred", exception);
            throw new HostUnavailableException();
        } catch (Exception exception) {
            LOGGER.error("Exception occurred", exception);
        }
        return null;
    }

    /**
     * Checks sms message status.
     *
     * @param request {@link CheckSmsMessageStatusResponse} request
     * @return {@link CheckSmsMessageStatusResponse} response
     * @throws ObjectNotFoundException  if sms not found
     * @throws InvalidTokenException    if token invalid
     * @throws HostUnavailableException if host unavailable
     * @throws UndefinedException       if exception undefined
     */
    public CheckSmsMessageStatusResponse checkSmsMessageStatus(CheckSmsMessageStatusRequest request)
        throws ObjectNotFoundException, InvalidTokenException, HostUnavailableException, UndefinedException {
        try {
            String url = String.format(CHECK_SMS_MESSAGE_STATUS_URL, request.getToken(), request.getMessageId());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(CheckSmsMessageStatusResponse.class);
        } catch (UniformInterfaceException exception) {
            UnisenderErrorHelper.throwCheckSmsMessageStatusException(exception.getResponse());
        } catch (ClientHandlerException exception) {
            LOGGER.error("Client handler exception occurred", exception);
            throw new HostUnavailableException();
        }
        return null;
    }

    /**
     * Gets message list.
     *
     * @param request {@link BaseUnisenderRequest} request
     * @return {@link GetMessageListResponse} response
     * @throws InvalidTokenException if token invalid
     * @throws HostUnavailableException if host unavailable
     * @throws UndefinedException    if exception undefined
     */
    public GetMessageListResponse getMessageList(BaseUnisenderRequest request)
        throws InvalidTokenException, HostUnavailableException, UndefinedException {
        try {
            String url = String.format(GET_MESSAGE_LIST_URL, request.getToken());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(GetMessageListResponse.class);
        } catch (UniformInterfaceException exception) {
            UnisenderErrorHelper.throwGetMessageListException(exception.getResponse());
        } catch (ClientHandlerException exception) {
            LOGGER.error("Client handler exception occurred", exception);
            throw new HostUnavailableException();
        }
        return null;
    }

    /**
     * Sends sms.
     *
     * @param request {@link SendSmsRequest} request
     * @return {@link SendSmsResponse} response
     * @throws IncorrectPhoneNumberException if phone number incorrect
     * @throws BillingException              if billing error
     * @throws ObjectNotFoundException       if message not found
     * @throws AccessDeniedException         if accent denied
     * @throws LimitExceededException        if limit exceeded
     * @throws InvalidTokenException         if invalid token
     * @throws HostUnavailableException if host unavailable
     * @throws UndefinedException            if exception undefined
     */
    public SendSmsResponse sendSms(SendSmsRequest request) throws LimitExceededException,
        BillingException, ObjectNotFoundException, AccessDeniedException,
        IncorrectPhoneNumberException, InvalidTokenException, HostUnavailableException, UndefinedException {
        try {
            String url = String.format(SEND_SMS_URL, request.getToken(), request.getMessageId(), request.getPhone());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(SendSmsResponse.class);
        } catch (UniformInterfaceException exception) {
            UnisenderErrorHelper.throwSendMessageException(exception.getResponse());
        } catch (ClientHandlerException exception) {
            LOGGER.error("Client handler exception occurred", exception);
            throw new HostUnavailableException();
        }
        return null;
    }

    /**
     * Gets sms limit amount.
     *
     * @param request {@link GetLimitRequest} request
     * @return {@link GetLimitResponse} response
     * @throws InvalidTokenException if token invalid
     * @throws HostUnavailableException if host unavailable
     * @throws UndefinedException    if exception undefined
     */
    public GetLimitResponse getLimit(GetLimitRequest request) throws InvalidTokenException, HostUnavailableException,
        UndefinedException {
        try {
            String url = String.format(GET_LIMIT_URL, request.getToken());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(GetLimitResponse.class);
        } catch (UniformInterfaceException exception) {
            UnisenderErrorHelper.throwGetLimitException(exception.getResponse());
        } catch (ClientHandlerException exception) {
            LOGGER.error("Client handler exception occurred", exception);
            throw new HostUnavailableException();
        }
        return null;
    }

    /**
     * Checks sms delivery status.
     *
     * @param request {@link CheckSmsRequest} request
     * @return {@link CheckSmsResponse} response
     * @throws ObjectNotFoundException if sms not found
     * @throws InvalidTokenException   if token invalid
     * @throws HostUnavailableException if host unavailable
     * @throws UndefinedException      if exception undefined
     */
    public CheckSmsResponse checkSms(CheckSmsRequest request) throws ObjectNotFoundException, InvalidTokenException,
        HostUnavailableException, UndefinedException {
        try {
            String url = String.format(CHECK_SMS_URL, request.getToken(), request.getSmsId());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(new GenericType<List<CheckSmsResponse>>() {
            }).get(0);
        } catch (UniformInterfaceException exception) {
            UnisenderErrorHelper.throwCheckSmsExcepiton(exception.getResponse());
        } catch (ClientHandlerException exception) {
            LOGGER.error("Client handler exception occurred", exception);
        }
        return null;
    }

}
