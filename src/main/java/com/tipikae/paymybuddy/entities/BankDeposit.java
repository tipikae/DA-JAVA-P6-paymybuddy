package com.tipikae.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Entity class for Bank deposit operations.
 * @author tipikae
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("BDEP")
public class BankDeposit extends Operation {
	 
	private static final long serialVersionUID = 1L;

	/**
	 * @param number
	 * @param dateOperation
	 * @param amount
	 * @param description
	 * @param account
	 */
	public BankDeposit(int number, Date dateOperation, double amount, String description, Account account) {
		super(number, dateOperation, amount, description, account);
	}

	public BankDeposit() {
		super();
	}
}
