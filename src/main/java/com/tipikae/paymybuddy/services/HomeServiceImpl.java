package com.tipikae.paymybuddy.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IUserRepository;

/**
 * Home Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class HomeServiceImpl implements IHomeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeServiceImpl.class);
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public HomeDTO getHome(String email) throws UserNotFoundException {
		LOGGER.debug("GetHome: email=" + email);
		Optional<User> optional = userRepository.findByEmail(email);
		if(!optional.isPresent()) {
			LOGGER.debug("GetHome: user with email=" + email + " not found.");
			throw new UserNotFoundException("User with email=" + email + " not found.");
		}
		
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setEmail(email);
		homeDTO.setBalance(optional.get().getAccount().getBalance());
		
		return homeDTO;
	}

}
