package com.tipikae.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Entity class for withdrawal operations.
 * @author tipikae
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("WIT")
public class Withdrawal extends Transfer {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Withdrawal() {
		super();
	}

	/**
	 * @param number
	 * @param dateOperation
	 * @param amount
	 * @param description
	 * @param account
	 */
	public Withdrawal(int number, Date dateOperation, double amount, String description, Account account) {
		super(number, dateOperation, amount, description, account);
	}
	
	
}