package com.tipikae.paymybuddy.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.repositories.AccountRepository;
import com.tipikae.paymybuddy.repositories.ClientRepository;
import com.tipikae.paymybuddy.repositories.UserRepository;
import com.tipikae.paymybuddy.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ClientRepository clientRepository;
	
	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private static UserServiceImpl userService;
	
	@BeforeAll
	private static void setUp() {
		userService = new UserServiceImpl();
	}

	@Test
	void registerNewUserThrowsUserAlreadyExistExceptionWhenEmailExists() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
		assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUser(new UserDTO()));
	}

}
