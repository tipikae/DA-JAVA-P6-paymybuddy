package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;

/**
 * Operation Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IOperationService {
	
	/**
	 * Get transfers details.
	 * @param srcEmail
	 * @return TransferDTO
	 * @throws UserNotFoundException
	 * @throws ConverterException 
	 */
	TransferDTO getTransfersDetails(String srcEmail) throws UserNotFoundException, ConverterException;

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
