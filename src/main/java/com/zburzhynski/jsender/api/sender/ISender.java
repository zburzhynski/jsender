package com.zburzhynski.jsender.api.sender;

import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.SendingStatus;

import java.util.List;

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
     * @return response
     */
    List<SendingStatus> send(Message message);

}
