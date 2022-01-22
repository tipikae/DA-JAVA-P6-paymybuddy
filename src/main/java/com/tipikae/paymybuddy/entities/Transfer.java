package com.tipikae.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Abstract class for transfer operations.
 * @author tipikae
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("TRA")
public class Transfer extends Operation {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "number_src_account_connection")
	private Account srcAccount;
	
	@ManyToOne
	@JoinColumn(name = "number_dest_account_connection")
	private Account destAccount;
	
	/**
	 * 
	 */
	public Transfer() {
		super();
	}
	/**
	 * @param number
	 * @param dateOperation
	 * @param amount
	 * @param description
	 * @param account
	 */
	public Transfer(int number, Date dateOperation, double amount, String description, double fee, Account account) {
		super(number, dateOperation, amount, description, fee, account);
	}
}
