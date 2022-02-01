package com.tipikae.paymybuddy.integration.services;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.dto.NewUserDTO;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
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

	@Test
	void registerNewUserThrowsUserAlreadyExistExceptionWhenEmailExists() {
		assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUser(existingUserDTO));
	}

	@Transactional
	@Test
	void registerNewUserReturnsUserWhenEmailNotFound() throws UserAlreadyExistException {
		assertEquals("victor@victor.com", userService.registerNewUser(newUserDTO).getEmail());
	}

}
