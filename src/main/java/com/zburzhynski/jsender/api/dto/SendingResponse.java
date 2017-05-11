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

    private String errorMessage;

    /**
     * Adds message status.
     *
     * @param status {@link MessageStatus} message status to add
     */
    public void addMessageStatus(MessageStatus status) {
        getMessageStatuses().add(status);
    }

    /**
     * Adds message statuses.
     *
     * @param statuses {@link MessageStatus} message statuses to add
     */
    public void addMessageStatuses(List<MessageStatus> statuses) {
        getMessageStatuses().addAll(statuses);
    }

    /**
     * Gets message response.
     *
     * @return message response
     */
    public List<MessageStatus> getMessageStatuses() {
        if (messageStatuses == null) {
            messageStatuses = new ArrayList<>();
        }
        return messageStatuses;
    }

    public void setMessageStatuses(List<MessageStatus> messageStatuses) {
        this.messageStatuses = messageStatuses;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
