package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.exceptions.ConverterException;
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
}
