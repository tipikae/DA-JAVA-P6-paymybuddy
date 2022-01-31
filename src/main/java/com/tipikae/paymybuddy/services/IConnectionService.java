package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.ContactDTO;
import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.exceptions.ConnectionForbiddenException;
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
	ContactDTO getAllConnections(String srcEmail) throws UserNotFoundException;
	
	/**
	 * Add a new Connection.
	 * @param srcEmail
	 * @param newContactDTO
	 * @throws UserNotFoundException
	 * @throws ConnectionForbiddenException 
	 */
	void addConnection(String srcEmail, NewContactDTO newContactDTO) 
			throws UserNotFoundException, ConnectionForbiddenException;
}
