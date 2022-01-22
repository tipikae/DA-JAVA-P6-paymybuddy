package com.tipikae.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity class for transfer operations.
 * @author tipikae
 * @version 1.0
 *
 */
@Entity
@DiscriminatorValue("TRA")
public class Transfer extends Operation {

	private static final long serialVersionUID = 1L;

	/**
	 * The source User object.
	 */
	@ManyToOne
	@JoinColumn(name = "email_src_connection")
	private User srcUser;

	/**
	 * The destination User object.
	 */
	@ManyToOne
	@JoinColumn(name = "email_dest_connection")
	private User destUser;
	
	/**
	 * {@inheritDoc}
	 */
	public Transfer() {
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
	public Transfer(int number, Date dateOperation, double amount, String description, double fee, Account account) {
		super(number, dateOperation, amount, description, fee, account);
	}
}
