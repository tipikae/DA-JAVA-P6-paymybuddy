package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tipikae.paymybuddy.converters.IConverterListUserToConnectionDTO;
import com.tipikae.paymybuddy.converters.IConverterUserToHomeDTO;
import com.tipikae.paymybuddy.converters.IConverterUserToProfileDTO;
import com.tipikae.paymybuddy.dto.ConnectionDTO;
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
	@Mock
	private IConverterListUserToConnectionDTO converterUserToConnectionDTO;
	
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
		account.setBalance(new BigDecimal(1000.0));
		User user = new User();
		user.setAccount(account);
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setBalance(account.getBalance());
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(converterUserToHomeDTO.convertToDTO(any(User.class))).thenReturn(homeDTO);
		assertEquals(new BigDecimal(1000), userService.getHomeDetails("bob@bob.com").getBalance());
	}

	@Test
	void getHomeThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.getHomeDetails("bob@bob.com"));
	}
	
	@Test
	void getPotentialConnectionsReturnsListConnectionDTOWhenOk() 
			throws UserNotFoundException, ConverterException {
		User alice = new User();
		alice.setEmail("alice@alice.com");
		User bob = new User();
		bob.setEmail("bob@bob.com");
		List<User> users = new ArrayList<>();
		users.add(alice);
		users.add(bob);
		ConnectionDTO aliceDTO = new ConnectionDTO();
		aliceDTO.setEmail(alice.getEmail());
		ConnectionDTO bobDTO = new ConnectionDTO();
		bob.setEmail(bob.getEmail());
		List<ConnectionDTO> connectionsDTO = new ArrayList<>();
		connectionsDTO.add(aliceDTO);
		connectionsDTO.add(bobDTO);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
		when(userRepository.getPotentialConnections(anyInt())).thenReturn(users);
		when(converterUserToConnectionDTO.convertToListDTOs(users)).thenReturn(connectionsDTO);
		assertEquals(2, userService.getPotentialConnections("prout@prout.com").size());
	}
	
	@Test
	void getPotentialConnectionsThrowsUserNotFoundExceptionWhenEmailNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.getPotentialConnections("alice@alice.com"));
	}
	
	@Test
	void getPotentialConnectionsThrowsConverterExceptionWhenConverterError() throws ConverterException {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));
		when(userRepository.getPotentialConnections(anyInt())).thenReturn(new ArrayList<>());
		doThrow(ConverterException.class).when(converterUserToConnectionDTO).convertToListDTOs(new ArrayList<>());
		assertThrows(ConverterException.class, () -> userService.getPotentialConnections("alice@alice.com"));
	}

}
