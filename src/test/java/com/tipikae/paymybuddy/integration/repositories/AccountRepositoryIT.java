package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.repositories.IAccountRepository;

@Transactional
@SpringBootTest
class AccountRepositoryIT {
	
	@Autowired
	private IAccountRepository accountRepository;

	@Test
	void testGetBalance() {
		assertEquals(1000.0, accountRepository.findByEmailUser("alice@alice.com").get().getBalance());
	}

	@Test
	void testGetFirstname() {
		assertEquals("Alice", accountRepository.findByEmailUser("alice@alice.com").get().getUser().getFirstname());
	}
}
