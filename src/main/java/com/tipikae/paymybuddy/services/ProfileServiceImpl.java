package com.tipikae.paymybuddy.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IUserRepository;

/**
 * Profile service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class ProfileServiceImpl implements IProfileService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileServiceImpl.class);
	
	@Autowired
	private IUserRepository userRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileDTO getProfile(String email) throws UserNotFoundException {
		LOGGER.debug("GetProfile: email=" + email);
		Optional<User> optional = userRepository.findByEmail(email);
		if(!optional.isPresent()) {
			LOGGER.debug("GetProfile: user with email=" + email + " not found.");
			throw new UserNotFoundException("User with email=" + email + " not found.");
		}
		
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setEmail(email);
		profileDTO.setFirstname(optional.get().getFirstname());
		profileDTO.setLastname(optional.get().getLastname());
		profileDTO.setDateCreated(optional.get().getAccount().getDateCreated());
		
		return profileDTO;
	}

}
