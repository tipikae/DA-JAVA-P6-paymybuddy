package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
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

import com.tipikae.paymybuddy.converters.IConverterListConnectionToConnectionDTO;
import com.tipikae.paymybuddy.converters.IConverterListTransferToTransactionDTO;
import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Operation;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IOperationRepository;
import com.tipikae.paymybuddy.services.IUserService;
import com.tipikae.paymybuddy.services.OperationServiceImpl;

@ExtendWith(MockitoExtension.class)
class OperationServiceTest {
	
	@Mock
	private IUserService userService;
	@Mock
	private IOperationRepository operationRepository;
	@Mock
	private IConverterListConnectionToConnectionDTO converterConnectionToConnectionDTO;
	@Mock
	private IConverterListTransferToTransactionDTO converterTransferToTransactionDTO;
	
	@InjectMocks
	private OperationServiceImpl operationService;

	@Test
	void getTransferThrowsExceptionWhenEmailNotFound() throws UserNotFoundException {
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> operationService.getTransfersDetails("alice@alice.com"));
	}
	
	@Test
	void getTransferReturnsDTOswhenOk() throws UserNotFoundException, ConverterException {
		User user = new User();
		user.setConnections(new ArrayList<>());
		when(userService.isUserExists(anyString())).thenReturn(user);
		when(operationRepository.findTransfersByIdSrc(anyInt())).thenReturn(new ArrayList<>());
		when(converterConnectionToConnectionDTO.convertToListDTOs(anyList())).thenReturn(new ArrayList<>());
		when(converterTransferToTransactionDTO.convertToListDTOs(anyList())).thenReturn(new ArrayList<>());
		assertEquals(0, operationService.getTransfersDetails("alice@alice.com").getConnections().size());
	}
	
	@Test
	void depositThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setTypeOperation("DEP");
		operationDTO.setAmount(new BigDecimal(1000.0));
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> operationService.operation("bob@bob.com", operationDTO));
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
		operationService.operation("bob@bob.com", operationDTO);
		verify(operationRepository, Mockito.times(1)).save(any(Operation.class));
	}

	@Test
	void withdrawalThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		NewOperationDTO operationDTO = new NewOperationDTO();
		operationDTO.setTypeOperation("WIT");
		operationDTO.setAmount(new BigDecimal(1000.0));
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> operationService.operation("bob@bob.com", operationDTO));
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
		operationService.operation("bob@bob.com", operationDTO);
		verify(operationRepository, Mockito.times(1)).save(any(Operation.class));
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
		assertThrows(OperationForbiddenException.class, () -> operationService.operation("bob@bob.com", operationDTO));
	}

}
