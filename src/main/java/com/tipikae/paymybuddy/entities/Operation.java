package com.tipikae.paymybuddy.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Abstract class for all operations
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "type",
		discriminatorType = DiscriminatorType.STRING,
		length = 3)
@Table(name = "operation")
public abstract class Operation implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Number.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "number")
	private int number;

	/**
	 * Operation date.
	 */
	@Column(name = "date_operation")
	private Date dateOperation;

	/**
	 * Amount.
	 */
	@Column(name = "amount")
	private BigDecimal amount;

	/**
	 * Account.
	 */
	@ManyToOne
	@JoinColumn(name = "id_account")
	private Account account;
	
	public Operation() {
		super();
	}

	/*public Operation(int number, Date dateOperation, BigDecimal amount, Account account) {
		super();
		this.number = number;
		this.dateOperation = dateOperation;
		this.amount = amount;
		this.account = account;
	}*/
	
}
