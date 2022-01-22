package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;

/**
 * User service interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IUserService {

	/**
	 * Register a new user.
	 * @param userDTO	UserDTO object.
	 * @return User
	 * @throws UserAlreadyExistException
	 */
	User registerNewUser(UserDTO userDTO) throws UserAlreadyExistException;

	/**
	 * Delete a user by email
	 * @param email
	 */
	void deleteUser(String email);
}
