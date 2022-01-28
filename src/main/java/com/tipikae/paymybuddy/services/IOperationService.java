package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.exception.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;

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
	 * @param operationDTO
	 * @throws UserNotFoundException
	 */
	void deposit(String email, OperationDTO operationDTO) throws UserNotFoundException;
	
	/**
	 * Withdrawal money to bank account.
	 * @param email
	 * @param operationDTO
	 * @throws UserNotFoundException, OperationForbiddenException
	 */
	void withdrawal(String email, OperationDTO operationDTO) 
			throws UserNotFoundException, OperationForbiddenException;
	
	/**
	 * Transfer money between friends.
	 * @param emailSrc
	 * @param newTransferDTO
	 */
	void transfer(String emailSrc, NewTransferDTO newTransferDTO) 
			throws UserNotFoundException, OperationForbiddenException;
}
