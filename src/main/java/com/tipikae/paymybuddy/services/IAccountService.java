package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;

/**
 * Account Service interface. 
 * @author tipikae
 * @version 1.0
 *
 */
public interface IAccountService {

	/**
	 * Deposit money from bank account.
	 * @param email
	 * @param operationDTO
	 * @throws UserNotFoundException
	 */
	void deposit(String email, NewOperationDTO operationDTO) throws UserNotFoundException;
	
	/**
	 * Withdrawal money to bank account.
	 * @param email
	 * @param operationDTO
	 * @throws UserNotFoundException, OperationForbiddenException
	 */
	void withdrawal(String email, NewOperationDTO operationDTO) 
			throws UserNotFoundException, OperationForbiddenException;
	
	/**
	 * Transfer money between friends.
	 * @param emailSrc
	 * @param newTransferDTO
	 */
	void transfer(String emailSrc, NewTransferDTO newTransferDTO) 
			throws UserNotFoundException, OperationForbiddenException;
}
