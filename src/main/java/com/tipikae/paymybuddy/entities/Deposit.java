package com.tipikae.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Entity class for deposit operations.
 * @author tipikae
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("DEP")
public class Deposit extends Operation {

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public Deposit() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * @param {@inheritDoc}
	 * @param {@inheritDoc}
	 * @param {@inheritDoc}
	 * @param {@inheritDoc}
	 * @param {@inheritDoc}
	 * @param {@inheritDoc}
	 */
	public Deposit(int number, Date dateOperation, double amount, String description, double fee, Account account) {
		super(number, dateOperation, amount, description, fee, account);
	}

	
}
