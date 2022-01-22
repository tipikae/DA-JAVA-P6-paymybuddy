package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.tipikae.paymybuddy.repositories.IAccountRepository;

@SpringBootTest
class AccountRepositoryIT {
	
	@Autowired
	private IAccountRepository accountRepository;

	@Transactional
	@Test
	void testGetBalance() {
		assertEquals(1000.0, accountRepository.getById(1).getBalance());
	}

	@Transactional
	@Test
	void testGetFirstname() {
		assertEquals("Alice", accountRepository.getById(1).getClient().getFirstname());
	}
	
	@Transactional
	@Test
	void testGetConnections() {
		assertEquals(2, accountRepository.getById(1).getSrcConnections().size());
	}
}
