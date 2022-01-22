package com.tipikae.paymybuddy.exceptions;

public class UserAlreadyExistException extends PayMyBuddyException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException() {
        super();
    }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(final String message) {
        super(message);
    }

    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
