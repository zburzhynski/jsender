package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.dto.Message;

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
     * @return true if sending is successful, else false
     */
    boolean send(Message message);

}
