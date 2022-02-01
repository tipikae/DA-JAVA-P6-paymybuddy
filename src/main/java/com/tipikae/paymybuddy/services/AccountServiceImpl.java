package com.tipikae.paymybuddy.services;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Deposit;
import com.tipikae.paymybuddy.entities.Operation;
import com.tipikae.paymybuddy.entities.Transfer;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.entities.Withdrawal;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IAccountRepository;

/**
 * Account Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class AccountServiceImpl implements IAccountService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	/**
	 * User Service.
	 */
	@Autowired
	private IUserService userService;
	
	/**
	 * Account repository.
	 */
	@Autowired
	private IAccountRepository accountRepository;
	
	/**
	 * Rate property from application.properties.
	 */
	@Value("${paymybuddy.rate}")
	private double rate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deposit(String email, NewOperationDTO operationDTO) throws UserNotFoundException {
		double amount = operationDTO.getAmount();
		LOGGER.debug("Deposit: email=" + email + " amount=" + amount);
		User user = userService.isUserExists(email);

		Account account = user.getAccount();
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
	public void withdrawal(String email, NewOperationDTO operationDTO) 
			throws UserNotFoundException, OperationForbiddenException {
		double amount = operationDTO.getAmount();
		LOGGER.debug("Withdrawal: email=" + email + " amount=" + amount);
		User user = userService.isUserExists(email);

		Account account = user.getAccount();
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
	public void transfer(String emailSrc, NewTransferDTO newTransferDTO) 
			throws UserNotFoundException, OperationForbiddenException {
		String emailDest = newTransferDTO.getDestEmail();
		String description = newTransferDTO.getDescription();
		double amount = newTransferDTO.getAmount();
		LOGGER.debug("Transfer: emailSrc=" + emailSrc + " emailDest=" + emailDest + " amount=" + amount);
		
		if(emailSrc.equals(emailDest)) {
			LOGGER.debug("EmailSrc and emailDest are identical");
			throw new OperationForbiddenException("EmailSrc and emailDest are identical");
		}
		
		User userSrc = userService.isUserExists(emailSrc);
		User userDest = userService.isUserExists(emailDest);

		Account accountSrc = userSrc.getAccount();
		List<Operation> operations = accountSrc.getOperations();
		double fee = amount * rate;
		Transfer transfer = new Transfer();
		transfer.setAccount(accountSrc);
		transfer.setAmount(amount);
		transfer.setDateOperation(new Date());
		transfer.setDescription(description);
		transfer.setDestUser(userDest);
		transfer.setSrcUser(userSrc);
		transfer.setFee(fee);
		operations.add(transfer);
		accountSrc.setOperations(operations);
		accountSrc.setBalance(accountSrc.getBalance() - amount - fee);
		accountRepository.save(accountSrc);
		// TODO
		// add amount to dest account
	}

}
