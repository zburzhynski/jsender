package com.zburzhynski.jsender.impl.rest.exception.unisender;

/**
 * Base unisender exception.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Vladimir Zburzhynski
 */
public class UnisenderException extends Exception {

    /**
     * Default constructor.
     */
    public UnisenderException() {
    }

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public UnisenderException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public UnisenderException(String message, Throwable cause) {
        super(message, cause);
    }

}
