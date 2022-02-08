package com.tipikae.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IOperationService;

@Transactional
@SpringBootTest
class OperationServiceIT {
	
	@Autowired
	private IOperationService operationService;

	@Test
	void getOperationsReturnsPageOperationDTOWhenOk() throws UserNotFoundException, ConverterException {
		Page<OperationDTO> pages = operationService.getOperations("alice@alice.com", 0, 5);
		assertEquals(3, pages.getContent().size());
	}

	@Test
	void getOperationsThrowsUserNotFoundExceptionWhenEmailNotFound() {
		assertThrows(UserNotFoundException.class, 
				() -> operationService.getOperations("test@test.com", 0, 5));
	}

	@Test
	void operationDepositWhenOk() throws UserNotFoundException, OperationForbiddenException {
		NewOperationDTO newOperationDTO = new NewOperationDTO();
		newOperationDTO.setAmount(new BigDecimal(500));
		newOperationDTO.setTypeOperation("WIT");
		String emailSrc = "alice@alice.com";
		operationService.operation(emailSrc, newOperationDTO);
	}

	@Test
	void operationDepositThrowsUserNotFoundExceptionWhenEmailNotFound() {
		NewOperationDTO newOperationDTO = new NewOperationDTO();
		newOperationDTO.setAmount(new BigDecimal(1000));
		newOperationDTO.setTypeOperation("DEP");
		String emailSrc = "test@test.com";
		assertThrows(UserNotFoundException.class, 
				() -> operationService.operation(emailSrc, newOperationDTO));
	}

	@Test
	void operationWithdrawalThrowsOperationForbiddenExceptionWhenAmountSupBalance() {
		NewOperationDTO newOperationDTO = new NewOperationDTO();
		newOperationDTO.setAmount(new BigDecimal(10000));
		newOperationDTO.setTypeOperation("WIT");
		String emailSrc = "alice@alice.com";
		assertThrows(OperationForbiddenException.class, 
				() -> operationService.operation(emailSrc, newOperationDTO));
	}
	
	@Test
	void transferWhenOk() throws UserNotFoundException, OperationForbiddenException {
		String emailSrc = "alice@alice.com";
		NewTransferDTO newTransferDTO = new NewTransferDTO();
		newTransferDTO.setAmount(new BigDecimal(50));
		newTransferDTO.setDescription("test");
		newTransferDTO.setDestEmail("bob@bob.com");
		operationService.transfer(emailSrc, newTransferDTO);
	}
	
	@Test
	void transferThrowsUserNotFoundExceptionWhenEmailNotFound() {
		String emailSrc = "test@test.com";
		NewTransferDTO newTransferDTO = new NewTransferDTO();
		newTransferDTO.setAmount(new BigDecimal(50));
		newTransferDTO.setDescription("test");
		newTransferDTO.setDestEmail("bob@bob.com");
		assertThrows(UserNotFoundException.class, 
				() -> operationService.transfer(emailSrc, newTransferDTO));
	}
	
	@Test
	void transferThrowsOperationForbiddenExceptionWhenEmailsEqual() {
		String emailSrc = "bob@bob.com";
		NewTransferDTO newTransferDTO = new NewTransferDTO();
		newTransferDTO.setAmount(new BigDecimal(50));
		newTransferDTO.setDescription("test");
		newTransferDTO.setDestEmail("bob@bob.com");
		assertThrows(OperationForbiddenException.class, 
				() -> operationService.transfer(emailSrc, newTransferDTO));
	}
	
	@Test
	void transferThrowsOperationForbiddenExceptionWhenAmountSupBalance() {
		String emailSrc = "alice@alice.com";
		NewTransferDTO newTransferDTO = new NewTransferDTO();
		newTransferDTO.setAmount(new BigDecimal(10000));
		newTransferDTO.setDescription("test");
		newTransferDTO.setDestEmail("bob@bob.com");
		assertThrows(OperationForbiddenException.class, 
				() -> operationService.transfer(emailSrc, newTransferDTO));
	}
}
