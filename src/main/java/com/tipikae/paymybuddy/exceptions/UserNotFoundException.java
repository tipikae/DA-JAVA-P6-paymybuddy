package com.tipikae.paymybuddy.exceptions;

/**
 * User not found exception.
 * @author tipikae
 * @version 1.0
 *
 */
public class UserNotFoundException extends PayMyBuddyException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

}
