package com.tipikae.paymybuddy.services;

/**
 * Operation Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IOperationService {

	/**
	 * Deposit money from bank account.
	 * @param email
	 * @param amount
	 */
	void deposit(String email, double amount);
	
	/**
	 * Withdrawal money to bank account.
	 * @param email
	 * @param amount
	 */
	void withdrawal(String email, double amount);
	
	/**
	 * Transfer money between friends.
	 * @param emailSrc
	 * @param emailDest
	 * @param amount
	 */
	void transfer(String emailSrc, String emailDest, double amount);
}
