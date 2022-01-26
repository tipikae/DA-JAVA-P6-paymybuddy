package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;

/**
 * Profile Service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IProfileService {

	/**
	 * Get the user profile.
	 * @param email
	 * @return ProfileDTO
	 * @throws UserNotFoundException 
	 */
	ProfileDTO getProfile(String email) throws UserNotFoundException;
}
