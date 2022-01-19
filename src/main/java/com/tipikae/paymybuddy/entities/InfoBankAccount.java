package com.tipikae.paymybuddy.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Info Bank Account entity.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@Entity
@Table(name = "bank_accounts")
public class InfoBankAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "iban")
	private String iban;
	
	@Column(name = "email_client")
	private String emailClient;
}
