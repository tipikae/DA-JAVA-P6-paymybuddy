package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;

/**
 * Home Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IHomeService {

	/**
	 * Get home.
	 * @param email
	 * @return HomeDTO
	 * @throws UserNotFoundException
	 */
	HomeDTO getHome(String email) throws UserNotFoundException;
}
