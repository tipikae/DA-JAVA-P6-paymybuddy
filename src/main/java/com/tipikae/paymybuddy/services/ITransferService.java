package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;

/** 
 * Transfer Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface ITransferService {

	/**
	 * Get all transfers.
	 * @param srcEmail
	 * @return TransferDTO
	 * @throws UserNotFoundException
	 */
	TransferDTO getTransfer(String srcEmail) throws UserNotFoundException;
}
