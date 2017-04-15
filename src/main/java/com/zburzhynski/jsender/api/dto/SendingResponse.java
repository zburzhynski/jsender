package com.zburzhynski.jsender.api.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Sending response.
 * <p/>
 * Date: 15.04.2017
 *
 * @author Nikita Shevtsou
 */
public class SendingResponse {

    private List<MessageStatus> messageStatuses;

    /**
     * Adds message status.
     *
     * @param status {@link MessageStatus} message status to add
     */
    public void addMessageStatus(MessageStatus status) {
        if (messageStatuses == null) {
            messageStatuses = new ArrayList<>();
        }
        messageStatuses.add(status);
    }

    /**
     * Adds message statuses.
     *
     * @param statuses {@link MessageStatus} message statuses to add
     */
    public void addMessageStatuses(List<MessageStatus> statuses) {
        if (messageStatuses == null) {
            messageStatuses = new ArrayList<>();
        }
        messageStatuses.addAll(statuses);
    }

    public List<MessageStatus> getMessageStatuses() {
        return messageStatuses;
    }

    public void setMessageStatuses(List<MessageStatus> messageStatuses) {
        this.messageStatuses = messageStatuses;
    }

}
