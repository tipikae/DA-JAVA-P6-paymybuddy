package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.tipikae.paymybuddy.repositories.ClientRepository;

@SpringBootTest
class ClientRepositoryIT {
	
	@Autowired
	private ClientRepository userRepository;

	@Test
	void testFindAll() {
		assertEquals(4, userRepository.findAll().size());
	}

	@Transactional
	@Test
	void testFindById() {
		assertEquals("Bob", userRepository.getById("bob@bob.com").getFirstname());
		assertEquals("BOB", userRepository.getById("bob@bob.com").getLastname());
	}

	@Transactional
	@Test
	void testGetIbanById() {
		assertEquals(2, userRepository.getById("alice@alice.com").getInfosBankAccount().size());
	}

	@Transactional
	@Test
	void testGetAccountById() {
		assertEquals(1, userRepository.getById("alice@alice.com").getAccount().getNumber());
		assertEquals(1000.0, userRepository.getById("alice@alice.com").getAccount().getBalance());
	}
}
