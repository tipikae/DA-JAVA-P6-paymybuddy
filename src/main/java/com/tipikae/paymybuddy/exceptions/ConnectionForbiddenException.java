/**
 * 
 */
package com.tipikae.paymybuddy.exceptions;

/**
 * Connection forbidden exception.
 * @author tipikae
 * @version 1.0
 *
 */
public class ConnectionForbiddenException extends PayMyBuddyException {

	private static final long serialVersionUID = 1L;

	public ConnectionForbiddenException(String message) {
		super(message);
	}
}
