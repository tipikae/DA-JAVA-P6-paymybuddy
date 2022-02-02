package com.tipikae.paymybuddy.entities;

import java.math.BigDecimal;
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
public class Withdrawal extends Operation {

	private static final long serialVersionUID = 1L;

	public Withdrawal() {
		super();
	}

	public Withdrawal(int number, Date dateOperation, BigDecimal amount, Account account) {
		super(number, dateOperation, amount, account);
	}
	
	
}
