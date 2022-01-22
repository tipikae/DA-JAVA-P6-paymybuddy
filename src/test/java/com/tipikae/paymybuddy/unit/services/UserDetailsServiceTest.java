package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.MyUserDetailsService;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
	
	@Mock
	private IUserRepository userRepository;
	
	@InjectMocks
	private static MyUserDetailsService userDetailsService;
	
	private static String email = "alice@alice.com"; 
	private static String password = "123456789";
	private static User user;
	
	@BeforeAll
	private static void setUp() {
		userDetailsService = new MyUserDetailsService();
		user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setRoles(new ArrayList<>());
	}

	@Test
	void testLoadUserByUsernameWhenFound() {
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		assertEquals(org.springframework.security.core.userdetails.User.class, 
				userDetailsService.loadUserByUsername(email).getClass());
	}

	@Test
	void testLoadUserByUsernameWhenNotFound() {
		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
	}

}
