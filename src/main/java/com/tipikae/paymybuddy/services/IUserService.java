package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;

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
	 * Get home details.
	 * @param email
	 * @return HomeDTO
	 * @throws UserNotFoundException
	 */
	HomeDTO getHomeDetails(String email) throws UserNotFoundException;
	
	/**
	 * Get profile details.
	 * @param email
	 * @return ProfileDTO
	 * @throws UserNotFoundException
	 */
	ProfileDTO getProfileDetails(String email) throws UserNotFoundException;
	
	/**
	 * Get transfers details.
	 * @param srcEmail
	 * @return TransferDTO
	 * @throws UserNotFoundException
	 */
	TransferDTO getTransfersDetails(String srcEmail) throws UserNotFoundException;
}
