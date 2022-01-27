package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.HomeServiceImpl;

@ExtendWith(MockitoExtension.class)
class HomeServiceTest {
	
	@Mock
	private IUserRepository userRepository;
	
	@InjectMocks
	private HomeServiceImpl homeService;

	@Test
	void getHomeReturnsHomeDTOWhenEmailFound() throws UserNotFoundException {
		Account account = new Account();
		account.setBalance(1000.0);
		User user = new User();
		user.setAccount(account);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		assertEquals(1000.0, homeService.getHome("bob@bob.com").getBalance());
	}

	@Test
	void getHomeThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> homeService.getHome("bob@bob.com"));
	}

}
