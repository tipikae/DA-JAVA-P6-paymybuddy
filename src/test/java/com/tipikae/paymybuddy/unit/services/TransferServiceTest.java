package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IOperationRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.TransferServiceImpl;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {
	
	@Mock
	private IUserRepository userRepository;

	@Mock
	private IOperationRepository operationRepository;
	
	@InjectMocks
	private TransferServiceImpl transferService;

	@Test
	void getTransferThrowsExceptionWhenEmailNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> transferService.getTransfer("alice@alice.com"));
	}
	
	@Test
	void getTransferReturnsDTOswhenOk() throws UserNotFoundException {
		User user = new User();
		user.setConnections(new ArrayList<>());
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(operationRepository.findTransfersByEmailSrc(anyString())).thenReturn(new ArrayList<>());
		assertEquals(0, transferService.getTransfer("alice@alice.com").getTransactions().size());
	}

}
