package com.tipikae.paymybuddy.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("BWIT")
public class BankWithdrawal extends Operation {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "iban_bankaccount")
	private InfoBankAccount ibanBankAccount;

	/**
	 * @param number
	 * @param dateOperation
	 * @param amount
	 * @param description
	 * @param account
	 */
	public BankWithdrawal(int number, Date dateOperation, double amount, String description, Account account) {
		super(number, dateOperation, amount, description, account);
	}
	
	public BankWithdrawal() {
		super();
	}
}
