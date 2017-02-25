package com.zburzhynski.jsender.impl.rest.exception;

/**
 * Jdent unavailable exception.
 * <p/>
 * Date: 21.06.2015
 *
 * @author Vladimir Zburzhynski
 */
public class JdentUnavailableException extends Exception {

    /**
     * Default constructor.
     */
    public JdentUnavailableException() {
    }

    /**
     * Constructor.
     *
     * @param message exception message
     */
    public JdentUnavailableException(String message) {
        super(message);
    }

    /**
     * Constructor.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public JdentUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
