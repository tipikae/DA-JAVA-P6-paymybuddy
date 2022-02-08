package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.tipikae.paymybuddy.converters.IConverterListConnectionToConnectionDTO;
import com.tipikae.paymybuddy.converters.IConverterPageOperationToOperationDTO;
import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.OperationDTO;
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
	private IConverterPageOperationToOperationDTO converterOperationToOperationDTO;
	@Mock
	private BigDecimal rate;
	
	@InjectMocks
	private OperationServiceImpl operationService;

	@Test
	void getOperationsThrowsExceptionWhenEmailNotFound() throws UserNotFoundException {
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> operationService.getOperations("alice@alice.com", 1, 5));
	}
	
	@Test
	void getOperationsReturnsDTOswhenOk() throws UserNotFoundException, ConverterException {
		Account account = new Account();
		User user = new User();
		user.setId(1);
		user.setConnections(new ArrayList<>());
		account.setIdUser(user.getId());
		user.setAccount(account);
		Page<Operation> page = new PageImpl(new ArrayList<Operation>());
		Page<OperationDTO> pageDTO = new PageImpl(new ArrayList<Operation>());
		when(userService.isUserExists(anyString())).thenReturn(user);
		when(operationRepository.findOperationsByIdSrc(anyInt(), any(Pageable.class)))
			.thenReturn(page);
		when(converterOperationToOperationDTO.convertToPageDTO(any(Page.class)))
				.thenReturn(pageDTO);
		assertEquals(0, operationService.getOperations("alice@alice.com", 1, 5).getNumberOfElements());
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
	
	@Test
	void transferWhenOK() throws UserNotFoundException, OperationForbiddenException {
		Account srcAccount = new Account();
		srcAccount.setBalance(new BigDecimal(1000));
		Account destAccount = new Account();
		destAccount.setBalance(new BigDecimal(2000));
		User alice = new User();
		alice.setEmail("alice@alice.com");
		alice.setAccount(srcAccount);
		User bob = new User();
		bob.setEmail("bob@bob.com");
		bob.setAccount(destAccount);
		NewTransferDTO transferDTO = new NewTransferDTO();
		transferDTO.setAmount(new BigDecimal(500));
		transferDTO.setDescription("test transfer");
		transferDTO.setDestEmail(bob.getEmail());
		when(userService.isUserExists(anyString())).thenReturn(alice, bob);
		operationService.transfer(alice.getEmail(), transferDTO);
		verify(operationRepository, Mockito.times(1)).save(any(Operation.class));
	}
	
	@Test
	void transferThrowsOperationForbiddenExceptionWhenEmailsEquals() {
		String emailSrc = "alice@alice.com";
		NewTransferDTO transferDTO = new NewTransferDTO();
		transferDTO.setAmount(new BigDecimal(500));
		transferDTO.setDescription("test transfer");
		transferDTO.setDestEmail(emailSrc);
		assertThrows(OperationForbiddenException.class, 
				() -> operationService.transfer(emailSrc, transferDTO));
	}
	
	@Test
	void transferThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		String emailSrc = "alice@alice.com";
		NewTransferDTO transferDTO = new NewTransferDTO();
		transferDTO.setAmount(new BigDecimal(500));
		transferDTO.setDescription("test transfer");
		transferDTO.setDestEmail("bob@bob.com");
		when(userService.isUserExists(emailSrc)).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, 
				() -> operationService.transfer(emailSrc, transferDTO));
	}
	
	@Test
	void transferThrowsOperationForbiddenExceptionWhenAmountSupBalance() throws UserNotFoundException {
		Account srcAccount = new Account();
		srcAccount.setBalance(new BigDecimal(500));
		Account destAccount = new Account();
		destAccount.setBalance(new BigDecimal(2000));
		User alice = new User();
		alice.setEmail("alice@alice.com");
		alice.setAccount(srcAccount);
		User bob = new User();
		bob.setEmail("bob@bob.com");
		bob.setAccount(destAccount);
		NewTransferDTO transferDTO = new NewTransferDTO();
		transferDTO.setAmount(new BigDecimal(1000));
		transferDTO.setDescription("test transfer");
		transferDTO.setDestEmail(bob.getEmail());
		when(userService.isUserExists(anyString())).thenReturn(alice, bob);
		assertThrows(OperationForbiddenException.class, 
				() -> operationService.transfer(alice.getEmail(), transferDTO));
	}

}
