package com.zburzhynski.jsender.impl.rest.exception.unisender;

/**
 * Not found exception.
 * <p/>
 * Date: 16.03.2017
 *
 * @author Vladimir Zburzhynski
 */
public class NotFoundException extends Exception {

    /**
     * Default constructor.
     */
    public NotFoundException() {
    }

    /**
     * Constructor.
     *
     * @param message message
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message message
     * @param cause cause
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
