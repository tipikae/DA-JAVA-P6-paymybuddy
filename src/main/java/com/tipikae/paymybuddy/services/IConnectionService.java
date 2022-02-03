package com.tipikae.paymybuddy.services;

import java.util.List;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
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
	 * Get connections.
	 * @param srcEmail
	 * @return List<ConnectionDTO>
	 * @throws UserNotFoundException
	 * @throws ConverterException
	 */
	List<ConnectionDTO> getConnections(String srcEmail) throws UserNotFoundException, ConverterException;
	
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
