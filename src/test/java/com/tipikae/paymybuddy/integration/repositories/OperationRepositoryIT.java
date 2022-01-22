package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.repositories.IAccountRepository;
import com.tipikae.paymybuddy.repositories.IOperationRepository;

@Transactional
@SpringBootTest
class OperationRepositoryIT {
	
	@Autowired
	private IOperationRepository operationRepository;
	@Autowired
	private IAccountRepository accountRepository;

	@Test
	void testFindAll() {
		assertEquals(5, operationRepository.findAll().size());
	}

	@Test
	void testFindByAccount() {
		Optional<Account> account = accountRepository.findByEmailUser("alice@alice.com");
		assertEquals("alice@alice.com", account.get().getEmailUser());
		assertEquals(3, operationRepository.findByAccount(account.get()).size());
	}
}
