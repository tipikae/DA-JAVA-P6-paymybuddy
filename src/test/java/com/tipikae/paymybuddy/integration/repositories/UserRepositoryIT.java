package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

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
	void testCount() {
		assertEquals(3, userRepository.count());
	}

	@Test
	void testFindAll() {
		assertEquals(3, userRepository.findAll().size());
	}

	@Test
	void testFindByEmail() {
		Optional<User> optional = userRepository.findByEmail("alice@alice.com");
		assertTrue(optional.isPresent());
		assertEquals("alice@alice.com", optional.get().getEmail());
		assertEquals("USER", optional.get().getRoles().get(0).getRole());
	}
	
	@Test
	void testFindById() {
		User user = userRepository.getById("bob@bob.com");
		assertEquals("Bob", user.getFirstname());
		assertEquals("BOB", user.getLastname());
	}
	
	@Test
	void testGetAccountById() {
		User user = userRepository.getById("alice@alice.com");
		assertEquals("alice@alice.com", user.getAccount().getEmailUser());
		assertEquals(1000.0, user.getAccount().getBalance());
	}
	
	@Test
	void testGetConnectionsById() {
		assertEquals(2, userRepository.getById("alice@alice.com").getConnections().size());
	}
	
	@Test
	void testGetPotentialFriends() {
		assertEquals(1, userRepository.getPotentialFriends("alice@alice.com").size());
	}
}
