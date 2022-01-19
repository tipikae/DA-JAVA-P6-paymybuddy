package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.entities.Operation;
import com.tipikae.paymybuddy.repositories.BillingRepository;
import com.tipikae.paymybuddy.repositories.OperationRepository;

@SpringBootTest
class BillingIT {
	
	@Autowired
	private BillingRepository billingRepository;
	
	@Autowired
	private OperationRepository operationRepository;

	@Test
	void testFindAll() {
		assertEquals(2, billingRepository.findAll().size());
	}

	@Transactional
	@Test
	void testFindByOperation() {
		Operation operation = operationRepository.getById(4);
		assertEquals(0.05, billingRepository.findByOperation(operation).getFee());
	}
}
