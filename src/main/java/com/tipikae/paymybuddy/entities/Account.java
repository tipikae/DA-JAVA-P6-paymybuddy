package com.tipikae.paymybuddy.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Account entity
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "accounts")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "number")
	private int number;
	
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Column(name = "balance")
	private double balance;
	
	@OneToOne(
			cascade = CascadeType.ALL)
	@JoinColumn(
			name = "email_user",
			referencedColumnName = "email")
	private User user;
}
