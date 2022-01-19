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
public class Deposit extends Transfer {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Deposit() {
		super();
	}

	/**
	 * @param number
	 * @param dateOperation
	 * @param amount
	 * @param description
	 * @param account
	 */
	public Deposit(int number, Date dateOperation, double amount, String description, Account account) {
		super(number, dateOperation, amount, description, account);
	}

	
}
