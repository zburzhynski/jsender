package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.dto.Message;

/**
 * Message preparer interface.
 * <p/>
 * Date: 24.02.2017
 *
 * @author Nikita Shevtsou
 */
public interface IMessagePreparer {

    /**
     * Prepares message template.
     *
     * @param message message to format
     */
    void prepareMessage(Message message);
}
