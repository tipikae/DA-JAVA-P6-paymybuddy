package com.tipikae.paymybuddy.exceptions;

/**
 * An exception when user already exists.
 * @author tipikae
 * @version 1.0
 *
 */
public class UserAlreadyExistException extends PayMyBuddyException {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public UserAlreadyExistException() {
        super();
    }

	/**
	 * {@inheritDoc}
	 * @param {@inheritDoc}
	 * @param {@inheritDoc}
	 */
    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     * @param {@inheritDoc}
     */
    public UserAlreadyExistException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     * @param {@inheritDoc}
     */
    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
