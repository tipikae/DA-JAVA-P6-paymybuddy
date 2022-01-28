package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exception.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IAccountRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.OperationServiceImpl;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {
	
	@Mock
	private IUserRepository userRepository;
	@Mock
	private IAccountRepository accountRepository;
	
	@InjectMocks
	private OperationServiceImpl operationService;

	@Test
	void depositThrowsUserNotFoundExceptionWhenEmailNotFound() {
		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setAmount(1000.0);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> operationService.deposit("bob@bob.com", operationDTO));
	}

	@Test
	void depositCallSaveWhenEmailFound() throws UserNotFoundException {
		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setAmount(1000.0);
		Account account = new Account();
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		operationService.deposit("bob@bob.com", operationDTO);
		verify(accountRepository, Mockito.times(1)).save(any(Account.class));
	}

	@Test
	void withdrawalThrowsUserNotFoundExceptionWhenEmailNotFound() {
		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setAmount(1000.0);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> operationService.withdrawal("bob@bob.com", operationDTO));
	}

	@Test
	void withdrawalCallSaveWhenEmailFound() throws UserNotFoundException, OperationForbiddenException {
		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setAmount(1000.0);
		Account account = new Account();
		account.setBalance(2000);
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		operationService.withdrawal("bob@bob.com", operationDTO);
		verify(accountRepository, Mockito.times(1)).save(any(Account.class));
	}

	@Test
	void withdrawalThrowsOperationForbiddenExceptionWhenBalanceNotEnough() 
			throws UserNotFoundException, OperationForbiddenException {
		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setAmount(1000.0);
		Account account = new Account();
		account.setBalance(500);
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		assertThrows(OperationForbiddenException.class, () -> operationService.withdrawal("bob@bob.com", operationDTO));
	}

}
