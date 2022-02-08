package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

/**
 * Class for composite primary key of Connection entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Embeddable
public class ConnectionId implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Source User id.
	 */
	private int idUserSrc;
	
	/**
	 * Destination User id.
	 */
	private int idUserDest;
	
	public ConnectionId() {
	}
	
	public ConnectionId(int idUserSrc, int idUserDest) {
		this.idUserSrc = idUserSrc;
		this.idUserDest = idUserDest;
	}

	/**
	 * {@inheritDoc}
	 */
	/*@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConnectionId other = (ConnectionId) obj;
        if (idUserSrc == 0) {
            if (other.idUserSrc != 0)
                return false;
        } else if (idUserSrc != other.idUserSrc)
            return false;
        if (idUserDest == 0) {
            if (other.idUserDest != 0)
                return false;
        } else if (idUserDest != other.idUserDest)
            return false;
        return true;
	}*/

	/**
	 * {@inheritDoc}
	 */
	/*@Override
	public int hashCode() {
		final int prime = 31;
        int result = 1;
        result = prime * result + ((idUserSrc == 0) ? 0 : Integer.valueOf(idUserSrc).hashCode());
        result = prime * result + ((idUserDest == 0) ? 0 : Integer.valueOf(idUserDest).hashCode());
        return result;
	}*/
	
}
