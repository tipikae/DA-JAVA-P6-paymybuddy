package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.repositories.IUserRepository;

@Transactional
@SpringBootTest
class UserRepositoryIT {
	
	@Autowired
	private IUserRepository userRepository;

	@Test
	void testFindByEmail() {
		Optional<User> optional = userRepository.findByEmail("alice@alice.com");
		assertTrue(optional.isPresent());
		assertEquals("alice@alice.com", optional.get().getEmail());
		assertEquals("USER", optional.get().getRoles().get(0).getRole());
	}
	
	@Test
	void testFindById() {
		User user = userRepository.getById(2);
		assertEquals("Bob", user.getFirstname());
		assertEquals("BOB", user.getLastname());
	}
	
	@Test
	void testGetAccountById() {
		User user = userRepository.getById(1);
		assertEquals(1, user.getAccount().getIdUser());
		assertEquals(1, user.getAccount().getBalance().compareTo(new BigDecimal(1000.0)));
	}
	@Test
	void testGetConnectionsById() {
		assertEquals(2, userRepository.getById(1).getConnections().size());
	}
}
