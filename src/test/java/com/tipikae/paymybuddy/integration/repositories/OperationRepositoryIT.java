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
	void testFindByAccount() {
		Optional<Account> account = accountRepository.findByIdUser(1);
		assertEquals(1, account.get().getIdUser());
		assertEquals(3, operationRepository.findByAccount(account.get()).size());
	}
	
	@Test
	void testFindTransfersByEmailSrc() {
		assertEquals(2, operationRepository.findTransfersByIdSrc(1).size());
	}
}
