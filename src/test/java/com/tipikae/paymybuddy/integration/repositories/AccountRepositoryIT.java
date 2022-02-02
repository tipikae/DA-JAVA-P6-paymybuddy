package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

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
		assertEquals(0, accountRepository.findByIdUser(1).get().getBalance().compareTo(new BigDecimal(1000.0)));
	}

	@Test
	void testGetFirstname() {
		assertEquals("Alice", accountRepository.findByIdUser(1).get().getUser().getFirstname());
	}
}
