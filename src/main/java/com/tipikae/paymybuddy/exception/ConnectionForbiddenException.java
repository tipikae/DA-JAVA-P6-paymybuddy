/**
 * 
 */
package com.tipikae.paymybuddy.exception;

/**
 * Connection forbidden exception.
 * @author tipikae
 * @version 1.0
 *
 */
public class ConnectionForbiddenException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConnectionForbiddenException() {
		super();
	}

	public ConnectionForbiddenException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ConnectionForbiddenException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ConnectionForbiddenException(String arg0) {
		super(arg0);
	}

	public ConnectionForbiddenException(Throwable arg0) {
		super(arg0);
	}

}
