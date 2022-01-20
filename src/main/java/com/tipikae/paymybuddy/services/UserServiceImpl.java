package com.tipikae.paymybuddy.services;

import java.util.Arrays;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Client;
import com.tipikae.paymybuddy.entities.Role;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.repositories.AccountRepository;
import com.tipikae.paymybuddy.repositories.ClientRepository;
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
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public User registerNewUser(UserDTO userDTO) throws UserAlreadyExistException {
		if(emailExists(userDTO.getEmail())) {
			throw new UserAlreadyExistException("An account with email address: " + userDTO.getEmail()
					+ " already exists.");
		}
		
		User user = new User();
		Role role = new Role();
		role.setRole("USER");
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
		
		Client client = new Client();
		client.setEmail(userDTO.getEmail());
		client.setFirstname(userDTO.getFirstname());
		client.setLastname(userDTO.getLastname());
		clientRepository.save(client);
		
		Account account = new Account();
		account.setBalance(0);
		account.setDateCreated(new Date());
		account.setClient(client);
		accountRepository.save(account);
		
		return user;
	}

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }


}
