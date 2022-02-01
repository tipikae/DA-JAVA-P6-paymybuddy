package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.tipikae.paymybuddy.repositories.IOperationRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.IUserService;
import com.tipikae.paymybuddy.services.OperationServiceImpl;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {
	
	@Mock
	private IUserService userService;
	@Mock
	private IUserRepository userRepository;
	@Mock
	private IAccountRepository accountRepository;
	@Mock
	private IOperationRepository operationRepository;
	
	@InjectMocks
	private OperationServiceImpl operationService;

	@Test
	void depositThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setAmount(1000.0);
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> operationService.deposit("bob@bob.com", operationDTO));
	}

	@Test
	void depositCallSaveWhenEmailFound() throws UserNotFoundException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setAmount(1000.0);
		Account account = new Account();
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userService.isUserExists(anyString())).thenReturn(user);
		operationService.deposit("bob@bob.com", operationDTO);
		verify(accountRepository, Mockito.times(1)).save(any(Account.class));
	}

	@Test
	void withdrawalThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setAmount(1000.0);
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> operationService.withdrawal("bob@bob.com", operationDTO));
	}

	@Test
	void withdrawalCallSaveWhenEmailFound() throws UserNotFoundException, OperationForbiddenException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setAmount(1000.0);
		Account account = new Account();
		account.setBalance(2000);
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userService.isUserExists(anyString())).thenReturn(user);
		operationService.withdrawal("bob@bob.com", operationDTO);
		verify(accountRepository, Mockito.times(1)).save(any(Account.class));
	}

	@Test
	void withdrawalThrowsOperationForbiddenExceptionWhenBalanceNotEnough() 
			throws UserNotFoundException, OperationForbiddenException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setAmount(1000.0);
		Account account = new Account();
		account.setBalance(500);
		account.setOperations(new ArrayList<>());
		User user = new User();
		user.setAccount(account);
		when(userService.isUserExists(anyString())).thenReturn(user);
		assertThrows(OperationForbiddenException.class, () -> operationService.withdrawal("bob@bob.com", operationDTO));
	}

	@Test
	void getTransferThrowsExceptionWhenEmailNotFound() throws UserNotFoundException {
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> operationService.getTransfersDetails("alice@alice.com"));
	}
	
	@Test
	void getTransferReturnsDTOswhenOk() throws UserNotFoundException {
		User user = new User();
		user.setConnections(new ArrayList<>());
		when(userService.isUserExists(anyString())).thenReturn(user);
		when(operationRepository.findTransfersByIdSrc(anyInt())).thenReturn(new ArrayList<>());
		assertEquals(0, operationService.getTransfersDetails("alice@alice.com").getTransactions().size());
	}

}
