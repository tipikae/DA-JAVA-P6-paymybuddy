package com.tipikae.paymybuddy.exceptions;

/**
 * Operation Forbidden exception.
 * @author tipikae
 * @version 1.0
 *
 */
public class OperationForbiddenException extends PayMyBuddyException {

	private static final long serialVersionUID = 1L;

	public OperationForbiddenException(String message) {
		super(message);
	}
}
