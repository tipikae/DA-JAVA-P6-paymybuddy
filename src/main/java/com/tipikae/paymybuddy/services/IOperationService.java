package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.TransferDTO;
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
	 * Save a deposit or withdrawal operation.
	 * @param email
	 * @param operationDTO
	 * @throws UserNotFoundException
	 * @throws OperationForbiddenException
	 */
	void operation(String email, NewOperationDTO operationDTO) 
			throws UserNotFoundException, OperationForbiddenException;

	/**
	 * Transfer money between users.
	 * @param emailSrc
	 * @param newTransferDTO
	 */
	void transfer(String emailSrc, NewTransferDTO newTransferDTO) 
			throws UserNotFoundException, OperationForbiddenException;
}
