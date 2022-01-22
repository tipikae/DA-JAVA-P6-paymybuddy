package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Class for composite primary key of Connection entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Embeddable
public class ConnectionId implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Source User email.
	 */
	private String emailUserSrc;
	
	/**
	 * Destination User email.
	 */
	private String emailUserDest;
	
	/**
	 * Default constructor.
	 */
	public ConnectionId() {
	}
	
	/**
	 * Constructor with fields.
	 * @param emailUserSrc
	 * @param emailUserDest
	 */
	public ConnectionId(String emailUserSrc, String emailUserDest) {
		this.emailUserSrc = emailUserSrc;
		this.emailUserDest = emailUserDest;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConnectionId other = (ConnectionId) obj;
        if (emailUserSrc == null) {
            if (other.emailUserSrc != null)
                return false;
        } else if (emailUserSrc != other.emailUserSrc)
            return false;
        if (emailUserDest == null) {
            if (other.emailUserDest != null)
                return false;
        } else if (emailUserDest != other.emailUserDest)
            return false;
        return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + ((emailUserSrc == null) ? 0 : Integer.valueOf(emailUserSrc).hashCode());
        result = prime * result + ((emailUserDest == null) ? 0 : Integer.valueOf(emailUserDest).hashCode());
        return result;
	}
	
}
