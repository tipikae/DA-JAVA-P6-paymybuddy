package com.tipikae.paymybuddy.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
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
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue("TRA")
public class Transfer extends Operation {

	private static final long serialVersionUID = 1L;

	/**
	 * Description.
	 */
	@Column(name = "description")
	private String description;

	/**
	 * Fee.
	 */
	@Column(name = "fee")
	private BigDecimal fee;

	/**
	 * The source User object.
	 */
	@ManyToOne
	@JoinColumn(name = "id_src_connection")
	private User srcUser;

	/**
	 * The destination User object.
	 */
	@ManyToOne
	@JoinColumn(name = "id_dest_connection")
	private User destUser;
	
	public Transfer() {
		super();
	}
	
	public Transfer(int number, Date dateOperation, BigDecimal amount, Account account, 
			String description, BigDecimal fee, User srcUser, User destUser) {
		super(number, dateOperation, amount, account);
		this.description = description;
		this.fee = fee;
		this.srcUser = srcUser;
		this.destUser = destUser;
	}
}
