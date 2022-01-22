package com.tipikae.paymybuddy.exceptions;

public class PayMyBuddyException extends Exception {

	private static final long serialVersionUID = 1L;

	public PayMyBuddyException() {
        super();
    }

    public PayMyBuddyException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PayMyBuddyException(final String message) {
        super(message);
    }

    public PayMyBuddyException(final Throwable cause) {
        super(cause);
    }
}
