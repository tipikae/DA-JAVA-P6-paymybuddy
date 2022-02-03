package com.tipikae.paymybuddy.services;

import org.springframework.data.domain.Page;

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.OperationDTO;
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
	 * Get operations.
	 * @param srcEmail
	 * @param page
	 * @param size
	 * @return Page<OperationDTO>
	 * @throws UserNotFoundException
	 * @throws ConverterException
	 */
	Page<OperationDTO> getOperations(String srcEmail, int page, int size) 
			throws UserNotFoundException, ConverterException;
	
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
