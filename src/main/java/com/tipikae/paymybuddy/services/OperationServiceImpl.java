package com.tipikae.paymybuddy.services;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.converters.IConverterPageOperationToOperationDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Deposit;
import com.tipikae.paymybuddy.entities.Transfer;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.entities.Withdrawal;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IOperationRepository;

/**
 * Operation Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class OperationServiceImpl implements IOperationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	/**
	 * User Service.
	 */
	@Autowired
	private IUserService userService;
	
	/**
	 * OperationRepository.
	 */
	@Autowired
	private IOperationRepository operationRepository;
	
	/**
	 * Converter Operation page to TransactionDTO page.
	 */
	@Autowired
	private IConverterPageOperationToOperationDTO converterOperationToTransactionDTO;
	
	/**
	 * Rate property from application.properties.
	 */
	@Value("${paymybuddy.rate}")
	private BigDecimal rate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<OperationDTO> getOperations(String srcEmail, int page, int size) 
			throws UserNotFoundException, ConverterException {
		LOGGER.debug("Getting operations for " + srcEmail);
		User user = userService.isUserExists(srcEmail);
		
		return converterOperationToTransactionDTO.convertToPageDTO(
				operationRepository.findOperationsByIdSrc(user.getAccount().getIdUser(), PageRequest.of(page, size)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void operation(String email, NewOperationDTO operationDTO)
			throws UserNotFoundException, OperationForbiddenException {
		BigDecimal amount = operationDTO.getAmount();
		String type = operationDTO.getTypeOperation();
		LOGGER.debug("Operation: email=" + email + " type=" + type + " amount=" + amount);
		User user = userService.isUserExists(email);
		
		switch(type) {
			case "DEP":
				deposit(user, amount);
				break;
			case "WIT":
				withdrawal(user, amount);
				break;
		}
	}

	private void deposit(User user, BigDecimal amount) {
		Account account = user.getAccount();
		Deposit deposit = new Deposit();
		deposit.setAccount(account);
		deposit.setAmount(amount);
		deposit.setDateOperation(new Date());
		account.setBalance(account.getBalance().add(amount));
		operationRepository.save(deposit);
	}

	private void withdrawal(User user, BigDecimal amount) throws OperationForbiddenException {
		Account account = user.getAccount();
		if(amount.compareTo(account.getBalance()) == 1 ) {
			LOGGER.debug("Withdrawal: amount(" + amount + ") > balance(" + account.getBalance() + ")");
			throw new OperationForbiddenException("Amount can't be more than balance.");
		}
		
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setAccount(account);
		withdrawal.setAmount(amount);
		withdrawal.setDateOperation(new Date());
		account.setBalance(account.getBalance().subtract(amount));
		operationRepository.save(withdrawal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void transfer(String emailSrc, NewTransferDTO newTransferDTO) 
			throws UserNotFoundException, OperationForbiddenException {
		String emailDest = newTransferDTO.getDestEmail();
		String description = newTransferDTO.getDescription();
		BigDecimal amount = newTransferDTO.getAmount();
		LOGGER.debug("Transfer: emailSrc=" + emailSrc + " emailDest=" + emailDest + " amount=" + amount);
		
		if(emailSrc.equals(emailDest)) {
			LOGGER.debug("EmailSrc and emailDest are identical");
			throw new OperationForbiddenException("Source and Destination are identical");
		}
		
		User userSrc = userService.isUserExists(emailSrc);
		User userDest = userService.isUserExists(emailDest);

		Account accountSrc = userSrc.getAccount();
		Account accountDest = userDest.getAccount();
		
		if(amount.compareTo(accountSrc.getBalance()) == 1 ) {
			LOGGER.debug("Transfer: amount(" + amount + ") > balance(" + accountSrc.getBalance() + ")");
			throw new OperationForbiddenException("Amount can't be more than balance.");
		}
		
		BigDecimal fee = amount.multiply(rate);
		Transfer transfer = new Transfer();
		transfer.setAccount(accountSrc);
		transfer.setAmount(amount);
		transfer.setDateOperation(new Date());
		transfer.setDescription(description);
		transfer.setDestUser(userDest);
		transfer.setSrcUser(userSrc);
		transfer.setFee(fee);
		accountSrc.setBalance(accountSrc.getBalance().subtract(amount).subtract(fee));
		accountDest.setBalance(accountDest.getBalance().add(amount));
		operationRepository.save(transfer); 
	}

}
