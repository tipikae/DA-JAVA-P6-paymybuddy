package com.tipikae.paymybuddy.services;

import java.util.List;

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
	 * @return List<TransferDTO>
	 * @throws UserNotFoundException
	 */
	List<TransferDTO> getTransfers(String srcEmail) throws UserNotFoundException;
}
