package com.tipikae.paymybuddy.services;

import java.util.List;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
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
	 * @return List<ConnectionDTO>
	 * @throws UserNotFoundException
	 */
	List<ConnectionDTO> getConnections(String srcEmail) throws UserNotFoundException;
}
