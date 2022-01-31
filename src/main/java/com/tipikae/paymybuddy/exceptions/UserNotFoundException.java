package com.tipikae.paymybuddy.exceptions;

/**
 * User not found exception.
 * @author tipikae
 * @version 1.0
 *
 */
public class UserNotFoundException extends PayMyBuddyException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
