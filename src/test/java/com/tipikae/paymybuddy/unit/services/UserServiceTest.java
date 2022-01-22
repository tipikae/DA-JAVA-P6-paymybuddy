package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.entities.Role;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Client;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.repositories.IAccountRepository;
import com.tipikae.paymybuddy.repositories.ClientRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private IUserRepository userRepository;
	
	@Mock
	private ClientRepository clientRepository;
	
	@Mock
	private IAccountRepository accountRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private static UserServiceImpl userService;
	
	private static UserDTO userDTO;
	private static User user;
	
	@BeforeAll
	private static void setUp() {
		userService = new UserServiceImpl();
		
		userDTO = new UserDTO();
		userDTO.setEmail("alice@alice.com");
		userDTO.setFirstname("Alice");
		userDTO.setLastname("ALICE");
		userDTO.setPassword("alice");
		userDTO.setConfirmedPassword("alice");
		
		user = new User();
		user.setEmail("alice@alice.com");
		user.setPassword("alice");
		user.setActive(true);
		Role role = new Role();
		role.setRole("USER");
		user.setRoles(Arrays.asList(role));
	}

	@Test
	void registerNewUserThrowsUserAlreadyExistExceptionWhenEmailExists() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
		assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUser(userDTO));
	}

	@Test
	void registerNewUserReturnsUserWhenEmailNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(passwordEncoder.encode(anyString())).thenReturn("");
		when(clientRepository.save(any(Client.class))).thenReturn(new Client());
		when(accountRepository.save(any(Account.class))).thenReturn(new Account());
		assertEquals("alice@alice.com", userService.registerNewUser(userDTO).getEmail());
	}

}
