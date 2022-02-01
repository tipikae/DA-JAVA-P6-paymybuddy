package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tipikae.paymybuddy.converters.IConverterUserToHomeDTO;
import com.tipikae.paymybuddy.converters.IConverterUserToProfileDTO;
import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.dto.NewUserDTO;
import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Role;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private IUserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private IConverterUserToHomeDTO converterUserToHomeDTO;
	@Mock
	private IConverterUserToProfileDTO converterUserToProfileDTO;
	
	@InjectMocks
	private static UserServiceImpl userService;
	
	private static NewUserDTO userDTO;
	private static User user;
	
	@BeforeAll
	private static void setUp() {
		userService = new UserServiceImpl();
		
		userDTO = new NewUserDTO();
		userDTO.setEmail("alice@alice.com");
		userDTO.setFirstname("Alice");
		userDTO.setLastname("ALICE");
		userDTO.setPassword("alice");
		userDTO.setConfirmedPassword("alice");
		
		user = new User();
		user.setEmail("alice@alice.com");
		user.setPassword("alice");
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
	void registerNewUserReturnsUserWhenEmailNotFound() throws UserAlreadyExistException {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(passwordEncoder.encode(anyString())).thenReturn("");
		assertEquals("alice@alice.com", userService.registerNewUser(userDTO).getEmail());
	}

	@Test
	void getProfileThrowsUserNotFoundExceptionWhenEmailNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.getProfileDetails("test@test.com"));
	}

	@Test
	void getProfileReturnsProfileDTOWhenEmailFound() throws UserNotFoundException, ConverterException {
		String email = "bob@bob.com";
		Account account = new Account();
		account.setDateCreated(new Date());
		User user = new User();
		user.setEmail(email);
		user.setFirstname("bob");
		user.setLastname("Bob");
		user.setAccount(account);
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setEmail(user.getEmail());
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(converterUserToProfileDTO.convertToDTO(user)).thenReturn(profileDTO);
		assertEquals(email, userService.getProfileDetails(email).getEmail());
	}

	@Test
	void getHomeReturnsHomeDTOWhenEmailFound() throws UserNotFoundException, ConverterException {
		Account account = new Account();
		account.setBalance(1000.0);
		User user = new User();
		user.setAccount(account);
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setBalance(account.getBalance());
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(converterUserToHomeDTO.convertToDTO(any(User.class))).thenReturn(homeDTO);
		assertEquals(1000.0, userService.getHomeDetails("bob@bob.com").getBalance());
	}

	@Test
	void getHomeThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.getHomeDetails("bob@bob.com"));
	}

}
