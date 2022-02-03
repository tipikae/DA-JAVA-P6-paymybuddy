package com.tipikae.paymybuddy.entities;

import java.math.BigDecimal;
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

	public Deposit() {
		super();
	}

	public Deposit(int number, Date dateOperation, BigDecimal amount, Account account) {
		super(number, dateOperation, amount, account);
	}

	
}
