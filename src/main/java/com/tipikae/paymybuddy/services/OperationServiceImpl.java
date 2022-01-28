package com.tipikae.paymybuddy.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Deposit;
import com.tipikae.paymybuddy.entities.Operation;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.entities.Withdrawal;
import com.tipikae.paymybuddy.exception.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IAccountRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;

/**
 * Operation Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class OperationServiceImpl implements IOperationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IAccountRepository accountRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deposit(String email, OperationDTO operationDTO) throws UserNotFoundException {
		double amount = operationDTO.getAmount();
		LOGGER.debug("Deposit: email=" + email + " amount=" + amount);
		Optional<User> optional = userRepository.findByEmail(email);
		if(!optional.isPresent()) {
			LOGGER.debug("Deposit: user with email=" + email + " not found.");
			throw new UserNotFoundException("User not found.");
		}

		Account account = optional.get().getAccount();
		List<Operation> operations = account.getOperations();
		Deposit deposit = new Deposit();
		deposit.setAccount(account);
		deposit.setAmount(amount);
		deposit.setDateOperation(new Date());
		operations.add(deposit);
		account.setOperations(operations);
		account.setBalance(account.getBalance() + amount);
		accountRepository.save(account);
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void withdrawal(String email, OperationDTO operationDTO) 
			throws UserNotFoundException, OperationForbiddenException {
		double amount = operationDTO.getAmount();
		LOGGER.debug("Withdrawal: email=" + email + " amount=" + amount);
		Optional<User> optional = userRepository.findByEmail(email);
		if(!optional.isPresent()) {
			LOGGER.debug("Withdrawal: user with email=" + email + " not found.");
			throw new UserNotFoundException("User not found.");
		}

		Account account = optional.get().getAccount();
		if(amount > account.getBalance()) {
			LOGGER.debug("Withdrawal: amount(" + amount + ") > balance(" + account.getBalance() + ")");
			throw new OperationForbiddenException("Amount can't be more than balance.");
		}
		List<Operation> operations = account.getOperations();
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setAccount(account);
		withdrawal.setAmount(amount);
		withdrawal.setDateOperation(new Date());
		operations.add(withdrawal);
		account.setOperations(operations);
		account.setBalance(account.getBalance() - amount);
		accountRepository.save(account);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void transfer(String emailSrc, String emailDest, double amount) {
		

	}

}
