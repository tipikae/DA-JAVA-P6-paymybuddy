package com.tipikae.paymybuddy.services;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Role;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.repositories.AccountRepository;
import com.tipikae.paymybuddy.repositories.UserRepository;

/**
 * Implementation of IUserService.
 * @author tipikae
 * @version 1.0
 *
 */
@Transactional
@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * {@inheritDoc}
	 * @param {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws {@inheritDoc}
	 */
	@Override
	public User registerNewUser(UserDTO userDTO) throws UserAlreadyExistException {
		LOGGER.debug("Registering new user");
		if(emailExists(userDTO.getEmail())) {
			LOGGER.debug("An user with email address: " + userDTO.getEmail()
				+ " already exists.");
			throw new UserAlreadyExistException("An user with email address: " + userDTO.getEmail()
				+ " already exists.");
		}
		
		User user = new User();
		Role role = new Role();
		role.setRole("USER");
		user.setEmail(userDTO.getEmail());
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRoles(Arrays.asList(role));
		
		Account account = new Account();
		account.setBalance(0);
		account.setDateCreated(new Date());
		account.setUser(userRepository.save(user));
		accountRepository.save(account);
		
		return user;
	}

    private boolean emailExists(String email) {
    	Optional<User> optional = userRepository.findByEmail(email);
    	if(optional.isPresent()) {
    		return true;
    	}
		return false;
    }
}
