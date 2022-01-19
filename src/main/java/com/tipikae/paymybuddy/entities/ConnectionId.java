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

	private int numberSrcAccount;
	private int numberDestAccount;
	
	/**
	 * @param numberSrcAccount
	 * @param numberDestAccount
	 */
	public ConnectionId(int numberSrcAccount, int numberDestAccount) {
		this.numberSrcAccount = numberSrcAccount;
		this.numberDestAccount = numberDestAccount;
	}
	
	public ConnectionId() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConnectionId other = (ConnectionId) obj;
        if (numberSrcAccount == 0) {
            if (other.numberSrcAccount != 0)
                return false;
        } else if (numberSrcAccount != other.numberSrcAccount)
            return false;
        if (numberDestAccount == 0) {
            if (other.numberDestAccount != 0)
                return false;
        } else if (numberDestAccount != other.numberDestAccount)
            return false;
        return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + ((numberSrcAccount == 0) ? 0 : Integer.valueOf(numberSrcAccount).hashCode());
        result = prime * result + ((numberDestAccount == 0) ? 0 : Integer.valueOf(numberDestAccount).hashCode());
        return result;
	}
	
}
