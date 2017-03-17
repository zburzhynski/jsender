package com.zburzhynski.jsender.impl.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
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
import com.zburzhynski.jsender.impl.rest.exception.unisender.AlphanameIncorrectException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageAlreadyExistException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.MessageToLongException;
import com.zburzhynski.jsender.impl.rest.exception.unisender.NotFoundException;
import com.zburzhynski.jsender.impl.rest.helper.UnisenderErrorHelper;
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

    private static final String CREATE_SMS_MESSAGE_URL =
        "http://sms.unisender.by/api/v1/createSmsMessage?token=%s&message=%s&alphaname_id=%s";

    private static final String CHECK_SMS_MESSAGE_STATUS_URL =
        "http://sms.unisender.by/api/v1/checkSMSMessageStatus?token=%s&message_id=%s";

    private static final String GET_MESSAGE_LIST_URL = "http://sms.unisender.by/api/v1/getMessagesList?token=%s";

    private static final String SEND_SMS_URL = "http://sms.unisender.by/api/v1/sendSms?token=%s&message_id=%s&phone=%s";

    private static final String GET_LIMIT_URL = "http://sms.unisender.by/api/v1/getLimit?token=%s";

    private static final String CHECK_SMS_URL = "http://sms.unisender.by/api/v1/checkSMS?token=%s&sms_id=%s";

    private Client client = Client.create(new DefaultClientConfig());


    /**
     * Creates sms message.
     *
     * @param request {@link CreateSmsMessageRequest} request
     * @return {@link CreateSmsMessageResponse} response
     * @throws MessageAlreadyExistException if message already exist
     * @throws AlphanameIncorrectException if alphaname incorrect
     * @throws MessageToLongException if message to long
     */
    public CreateSmsMessageResponse createSmsMessage(CreateSmsMessageRequest request)
        throws MessageAlreadyExistException, AlphanameIncorrectException, MessageToLongException {
        try {
            String url = String.format(CREATE_SMS_MESSAGE_URL, request.getToken(), request.getMessage(),
                request.getAlphanameId());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(CreateSmsMessageResponse.class);
        } catch (UniformInterfaceException exception) {
            UnisenderErrorHelper.throwCreateSmsMessageException(exception.getResponse());
            LOGGER.error("UniformInterfaceException", exception);
            return null;
        } catch (ClientHandlerException exception) {
            LOGGER.error("ClientHandlerException", exception);
            return null;
        }
    }

    /**
     * Checks sms message status.
     *
     * @param request {@link CheckSmsMessageStatusResponse} request
     * @return {@link CheckSmsMessageStatusResponse} response
     */
    public CheckSmsMessageStatusResponse checkSmsMessageStatus(CheckSmsMessageStatusRequest request) {
        try {
            String url = String.format(CHECK_SMS_MESSAGE_STATUS_URL, request.getToken(), request.getMessageId());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(CheckSmsMessageStatusResponse.class);
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            LOGGER.error("Exception", exception);
            return null;
        }
    }

    /**
     * Gets message list.
     *
     * @param request {@link BaseUnisenderRequest} request
     * @return {@link GetMessageListResponse} response
     */
    public GetMessageListResponse getMessageList(BaseUnisenderRequest request) {
        try {
            String url = String.format(GET_MESSAGE_LIST_URL, request.getToken());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(GetMessageListResponse.class);
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            LOGGER.error("Exception", exception);
            return null;
        }
    }

    /**
     * Sends sms.
     *
     * @param request {@link SendSmsRequest} request
     * @return {@link SendSmsResponse} response
     */
    public SendSmsResponse sendSms(SendSmsRequest request) {
        try {
            String url = String.format(SEND_SMS_URL, request.getToken(), request.getMessageId(), request.getPhone());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(SendSmsResponse.class);
        } catch (UniformInterfaceException | ClientHandlerException exception) {
            LOGGER.error("Exception", exception);
            return null;
        }
    }

    /**
     * Gets sms limit amount.
     *
     * @param request {@link GetLimitRequest} request
     * @return {@link GetLimitResponse} response
     */
    public GetLimitResponse getLimit(GetLimitRequest request) {
        try {
            String url = String.format(GET_LIMIT_URL, request.getToken());
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
     * @param request {@link CheckSmsRequest} request
     * @return {@link CheckSmsResponse} response
     * @throws NotFoundException if sms not found
     */
    public CheckSmsResponse checkSms(CheckSmsRequest request) throws NotFoundException {
        try {
            String url = String.format(CHECK_SMS_URL, request.getToken(), request.getSmsId());
            WebResource webResource = client.resource(url);
            return webResource.accept(MediaType.APPLICATION_XML).get(CheckSmsResponse.class);
        } catch (UniformInterfaceException exception) {
            LOGGER.error("UniformInterfaceException", exception);
            return null;
        } catch (ClientHandlerException exception) {
            LOGGER.error("ClientHandlerException", exception);
            return null;
        }
    }

}
