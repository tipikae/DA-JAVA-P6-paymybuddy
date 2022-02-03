package com.tipikae.paymybuddy.exceptions;

/**
 * Global exception.
 * @author tipikae
 * @version 1.0
 *
 */
public class PayMyBuddyException extends Exception {

	private static final long serialVersionUID = 1L;

	public PayMyBuddyException(final String message) {
        super(message);
    }
}
