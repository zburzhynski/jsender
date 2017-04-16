package com.zburzhynski.jsender.api.sender;

import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.SendingResponse;
import com.zburzhynski.jsender.api.exception.SendingException;

import java.util.Set;

/**
 * Message sender.
 * <p/>
 * Date: 13.02.2017
 *
 * @author Nikita Shevtsov
 */
public interface ISender {

    /**
     * Send message.
     *
     * @param message message to send
     * @return {@link SendingResponse} sending response
     * @throws SendingException if any
     */
    SendingResponse send(Message message) throws SendingException;

    /**
     * Get sending services.
     *
     * @return sending services
     */
    Set<SendingServices> getSendingServices();

    /**
     * Gets sending type.
     *
     * @return sending type
     */
    SendingType getSendingType();

}
