package com.tipikae.paymybuddy.unit.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.tipikae.paymybuddy.repositories.AccountRepository;

@SpringBootTest
class AccountTest {
	
	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	@Test
	void testGetBalance() {
		assertEquals(1000.0, accountRepository.getById(1).getBalance());
	}

	@Transactional
	@Test
	void testGetFirstname() {
		assertEquals("Alice", accountRepository.getById(1).getUser().getFirstname());
	}
}
