package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.ContactDTO;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;

/**
 * Connection Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IConnectionService {

	/**
	 * Get all connections of a user.
	 * @param srcEmail
	 * @return ContactDTO
	 * @throws UserNotFoundException
	 */
	ContactDTO getContact(String srcEmail) throws UserNotFoundException;
	
	//void addConnection(String srcEmail, String );
}
