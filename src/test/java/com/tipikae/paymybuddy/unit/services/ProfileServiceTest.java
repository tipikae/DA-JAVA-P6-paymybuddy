package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;
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
import com.tipikae.paymybuddy.services.ProfileServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {
	
	@Mock
	private IUserRepository userRepository;
	
	@InjectMocks
	private ProfileServiceImpl profileService;

	@Test
	void getProfileThrowsUserNotFoundExceptionWhenEmailNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> profileService.getProfile("test@test.com"));
	}

	@Test
	void getProfileReturnsProfileDTOWhenEmailFound() throws UserNotFoundException {
		String email = "bob@bob.com";
		Account account = new Account();
		account.setDateCreated(new Date());
		User user = new User();
		user.setEmail(email);
		user.setFirstname("bob");
		user.setLastname("Bob");
		user.setAccount(account);
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		assertEquals(email, profileService.getProfile(email).getEmail());
	}

}
