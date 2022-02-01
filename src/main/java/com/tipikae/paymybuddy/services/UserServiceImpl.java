package com.tipikae.paymybuddy.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.dto.TransactionDTO;
import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.dto.NewUserDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.Role;
import com.tipikae.paymybuddy.entities.Transfer;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IOperationRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;

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

	/**
	 * UserRepository.
	 */
	@Autowired
	private IUserRepository userRepository;
	
	/**
	 * OperationRepository.
	 */
	@Autowired
	private IOperationRepository operationRepository;
	
	/**
	 * PasswordEncoder bean.
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User registerNewUser(NewUserDTO userDTO) throws UserAlreadyExistException {
		LOGGER.debug("Registering new user");
		if(emailExists(userDTO.getEmail())) {
			LOGGER.debug("An user with email address: " + userDTO.getEmail()
				+ " already exists.");
			throw new UserAlreadyExistException("An user with email address: " + userDTO.getEmail()
				+ " already exists.");
		}

		User user = new User();
		Account account = new Account();
		Role role = new Role();
		
		role.setRole("USER");
		
		user.setEmail(userDTO.getEmail());
		user.setFirstname(userDTO.getFirstname());
		user.setLastname(userDTO.getLastname());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setRoles(Arrays.asList(role));
		user.setAccount(account);
		
		account.setBalance(0);
		account.setDateCreated(new Date());
		account.setUser(user);
		
		userRepository.save(user);
		
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HomeDTO getHomeDetails(String email) throws UserNotFoundException {
		LOGGER.debug("GetHomeDetails: email=" + email);
		Optional<User> optional = userRepository.findByEmail(email);
		if(!optional.isPresent()) {
			LOGGER.debug("GetHomeDetails: user with email=" + email + " not found.");
			throw new UserNotFoundException("User with email=" + email + " not found.");
		}
		
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setEmail(email);
		homeDTO.setBalance(optional.get().getAccount().getBalance());
		
		return homeDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileDTO getProfileDetails(String email) throws UserNotFoundException {
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TransferDTO getTransfersDetails(String srcEmail) throws UserNotFoundException {
		LOGGER.debug("Getting transfer for " + srcEmail);
		Optional<User> optional = userRepository.findByEmail(srcEmail);
		if(!optional.isPresent()) {
			LOGGER.debug("GetTransfer: user with email=" + srcEmail + " not found.");
			throw new UserNotFoundException("User not found.");
		}
		
		List<ConnectionDTO> connections = getConnections(optional.get());
		List<TransactionDTO> transactions = getTransactions(optional.get());
		TransferDTO transferDTO = new TransferDTO();
		transferDTO.setConnections(connections);
		transferDTO.setTransactions(transactions);
		
		return transferDTO;
	}
	
	private List<ConnectionDTO> getConnections(User srcUser) {
		List<ConnectionDTO> connections = new ArrayList<>();
		for(Connection connection: srcUser.getConnections()) {
			User destUser = connection.getDestUser();
			ConnectionDTO connectionDTO = new ConnectionDTO();
			connectionDTO.setEmail(destUser.getEmail());
			connectionDTO.setFirstname(destUser.getFirstname());
			connectionDTO.setLastname(destUser.getLastname());
			connections.add(connectionDTO);
		}
		
		return connections;
	}
	
	private List<TransactionDTO> getTransactions(User srcUser) {
		List<TransactionDTO> transactions = new ArrayList<>();
		List<Transfer> transfers = operationRepository.findTransfersByIdSrc(srcUser.getId());
		for(Transfer transfer: transfers) {
			TransactionDTO transactionDTO = new TransactionDTO();
			transactionDTO.setConnection(transfer.getDestUser().getFirstname());
			transactionDTO.setDescription(transfer.getDescription());
			transactionDTO.setAmount(transfer.getAmount());
			transactions.add(transactionDTO);
		}
		
		return transactions;
	}

    private boolean emailExists(String email) {
    	Optional<User> optional = userRepository.findByEmail(email);
    	if(optional.isPresent()) {
    		return true;
    	}
		return false;
    }
}
