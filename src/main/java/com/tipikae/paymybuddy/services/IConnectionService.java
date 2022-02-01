package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.ContactDTO;
import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.exceptions.ConnectionForbiddenException;
import com.tipikae.paymybuddy.exceptions.ConverterException;
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
	 * @throws ConverterException 
	 */
	ContactDTO getConnectionsDetails(String srcEmail) throws UserNotFoundException, ConverterException;
	
	/**
	 * Add a new Connection.
	 * @param srcEmail
	 * @param newContactDTO
	 * @return Connection
	 * @throws UserNotFoundException
	 * @throws ConnectionForbiddenException 
	 */
	Connection addConnection(String srcEmail, NewContactDTO newContactDTO) 
			throws UserNotFoundException, ConnectionForbiddenException;
}
