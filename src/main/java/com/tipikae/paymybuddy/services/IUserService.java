package com.tipikae.paymybuddy.services;

import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.dto.NewUserDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;
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
	User registerNewUser(NewUserDTO userDTO) throws UserAlreadyExistException;
	
	/**
	 * Get home details.
	 * @param email
	 * @return HomeDTO
	 * @throws UserNotFoundException
	 * @throws ConverterException 
	 */
	HomeDTO getHomeDetails(String email) throws UserNotFoundException, ConverterException;
	
	/**
	 * Get profile details.
	 * @param email
	 * @return ProfileDTO
	 * @throws UserNotFoundException
	 * @throws ConverterException 
	 */
	ProfileDTO getProfileDetails(String email) throws UserNotFoundException, ConverterException;
	
	/**
	 * Get bank page.
	 * @param email
	 * @throws UserNotFoundException
	 */
	void getBank(String email) throws UserNotFoundException;

	/**
	 * Check if a user exists or not. Only for others services.
	 * @param email
	 * @return User
	 * @throws UserNotFoundException
	 */
	User isUserExists(String email) throws UserNotFoundException;
}
