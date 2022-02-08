package com.tipikae.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.dto.NewUserDTO;
import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IUserService;

@SpringBootTest
class UserServiceIT {
	
	@Autowired
	private IUserService userService;
	
	
	private static NewUserDTO existingUserDTO;
	private static NewUserDTO newUserDTO;
	private static String newUserEmail;
	
	@BeforeAll
	private static void setUp() {
		existingUserDTO = new NewUserDTO();
		existingUserDTO.setEmail("alice@alice.com");
		existingUserDTO.setFirstname("Alice");
		existingUserDTO.setLastname("ALICE");
		existingUserDTO.setPassword("alice");
		existingUserDTO.setConfirmedPassword("alice");
		
		newUserEmail = "victor@victor.com";

		newUserDTO = new NewUserDTO();
		newUserDTO.setEmail(newUserEmail);
		newUserDTO.setFirstname("Victor");
		newUserDTO.setLastname("VICTOR");
		newUserDTO.setPassword("victor");
		newUserDTO.setConfirmedPassword("victor");
	}

	@Transactional
	@Test
	void registerNewUserReturnsUserWhenOk() throws UserAlreadyExistException {
		assertEquals("victor@victor.com", userService.registerNewUser(newUserDTO).getEmail());
	}

	@Transactional
	@Test
	void registerNewUserThrowsUserAlreadyExistExceptionWhenEmailExists() {
		assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUser(existingUserDTO));
	}

	@Test
	void getPotentialConnectionsReturnsListConnectionDTOWhenOk() 
			throws UserNotFoundException, ConverterException {
		List<ConnectionDTO> connections = userService.getPotentialConnections("bob@bob.com");
		assertEquals("alice@alice.com", connections.get(0).getEmail());
	}

	@Test
	void getPotentialConnectionsThrowsUserNotFoundWhenEmailNotFound() {
		assertThrows(UserNotFoundException.class, () -> userService.getPotentialConnections("test@test.com"));
	}
	
	@Test
	void getHomeDetailsReturnsHomeDTOWhenOk() throws UserNotFoundException, ConverterException {
		HomeDTO homeDTO = userService.getHomeDetails("alice@alice.com");
		assertEquals("Alice", homeDTO.getFirstname());
	}
	
	@Test
	void getHomeDetailsThrowsUserNotFoundExceptionWhenEmailNotFound() {
		assertThrows(UserNotFoundException.class, () -> userService.getHomeDetails("test@test.com"));
	}
	
	@Test
	void getProfileReturnsProfileDTOWhenOk() throws UserNotFoundException, ConverterException {
		ProfileDTO profileDTO = userService.getProfileDetails("alice@alice.com");
		assertEquals("Alice", profileDTO.getFirstname());
	}
	
	@Test
	void getProfileThrowsUserNotFoundExceptionWhenEmailNotFound() {
		assertThrows(UserNotFoundException.class, () -> userService.getProfileDetails("test@test.com"));
	}
	
	@Test
	void getBankWhenOk() throws UserNotFoundException {
		userService.getBank("alice@alice.com");
	}
	
	@Test
	void getBankThrowsUserNotFoundExceptionWhenEmailNotFound() {
		assertThrows(UserNotFoundException.class, () -> userService.getBank("test@test.com"));
	}

	@Test
	void isUserExistsWhenOk() throws UserNotFoundException {
		assertEquals("ALICE", userService.isUserExists("alice@alice.com").getLastname());
	}
	
	@Test
	void isUserExistsThrowsUserNotFoundExceptionWhenEmailNotFound() {
		assertThrows(UserNotFoundException.class, () -> userService.isUserExists("test@test.com"));
	}
}
