package com.tipikae.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity class for transfer operations.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
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
	
	public Transfer() {
		super();
	}
	
	public Transfer(int number, Date dateOperation, double amount, String description, 
			double fee, Account account, User scrUser, User destUSer) {
		super(number, dateOperation, amount, description, fee, account);
		this.srcUser = scrUser;
		this.destUser = destUSer;
	}
}
