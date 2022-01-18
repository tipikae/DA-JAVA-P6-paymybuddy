package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

/**
 * Class for composite primary key of Connection entity.
 * @author tipikae
 * @version 1.0
 *
 */
public class ConnectionId implements Serializable {

	private static final long serialVersionUID = 1L;

	private int numberAccount1;
	private int numberAccount2;
	
	/**
	 * @param numberAccount1
	 * @param numberAccount2
	 */
	public ConnectionId(int numberAccount1, int numberAccount2) {
		this.numberAccount1 = numberAccount1;
		this.numberAccount2 = numberAccount2;
	}
}
