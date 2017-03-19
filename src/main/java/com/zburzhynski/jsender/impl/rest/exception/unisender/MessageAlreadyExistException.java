package com.zburzhynski.jsender.impl.rest.exception.unisender;

/**
 * Message already exist.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Vladimir Zburzhynski
 */
public class MessageAlreadyExistException extends UnisenderException {

    private Integer messageId;

    /**
     * Default constructor.
     */
    public MessageAlreadyExistException() {
    }

    /**
     * Constructor.
     *
     * @param messageId message id
     */
    public MessageAlreadyExistException(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

}
