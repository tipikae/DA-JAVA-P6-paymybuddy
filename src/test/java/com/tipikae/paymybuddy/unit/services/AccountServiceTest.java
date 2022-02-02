package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IAccountRepository;
import com.tipikae.paymybuddy.services.AccountServiceImpl;
import com.tipikae.paymybuddy.services.IUserService;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@Mock
	private IUserService userService;
	@Mock
	private IAccountRepository accountRepository;
	
	@InjectMocks
	private AccountServiceImpl accountService;
	
	@Test
	void depositThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setTypeOperation("DEP");
		operationDTO.setAmount(new BigDecimal(1000.0));
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> accountService.operation("bob@bob.com", operationDTO));
	}

	@Test
	void depositCallSaveWhenEmailFound() throws UserNotFoundException, OperationForbiddenException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setTypeOperation("DEP");
		operationDTO.setAmount(new BigDecimal(1000.0));
		Account account = new Account();
		account.setBalance(new BigDecimal(0));
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userService.isUserExists(anyString())).thenReturn(user);
		accountService.operation("bob@bob.com", operationDTO);
		verify(accountRepository, Mockito.times(1)).save(any(Account.class));
	}

	@Test
	void withdrawalThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setTypeOperation("WIT");
		operationDTO.setAmount(new BigDecimal(1000.0));
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> accountService.operation("bob@bob.com", operationDTO));
	}

	@Test
	void withdrawalCallSaveWhenEmailFound() throws UserNotFoundException, OperationForbiddenException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setTypeOperation("WIT");
		operationDTO.setAmount(new BigDecimal(1000.0));
		Account account = new Account();
		account.setBalance(new BigDecimal(2000.0));
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userService.isUserExists(anyString())).thenReturn(user);
		accountService.operation("bob@bob.com", operationDTO);
		verify(accountRepository, Mockito.times(1)).save(any(Account.class));
	}

	@Test
	void withdrawalThrowsOperationForbiddenExceptionWhenBalanceNotEnough() 
			throws UserNotFoundException, OperationForbiddenException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setTypeOperation("WIT");
		operationDTO.setAmount(new BigDecimal(1000.0));
		Account account = new Account();
		account.setBalance(new BigDecimal(500.0));
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userService.isUserExists(anyString())).thenReturn(user);
		assertThrows(OperationForbiddenException.class, () -> accountService.operation("bob@bob.com", operationDTO));
	}

}
