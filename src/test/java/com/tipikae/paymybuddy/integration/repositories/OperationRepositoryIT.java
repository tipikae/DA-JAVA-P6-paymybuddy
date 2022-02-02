package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.repositories.IOperationRepository;

@SpringBootTest
class OperationRepositoryIT {
	
	@Autowired
	private IOperationRepository operationRepository;

	@Test
	void testFindOperationsByEmailSrc() {
		assertEquals(9, operationRepository.findOperationsByIdSrc(1).size());
	}
}
