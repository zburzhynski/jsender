package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.Recipient;

import java.util.List;
import java.util.Map;

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
     * @param messages messages to send
     * @return response
     */
    Map<Recipient, String> send(List<Message> messages);

}
