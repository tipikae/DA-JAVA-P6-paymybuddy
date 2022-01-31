package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.repositories.IConnectionRepository;

@Transactional
@SpringBootTest
class ConnectionRepositoryIT {
	
	@Autowired
	private IConnectionRepository connectionRepository;

	@Test
	void testFindByConnectionId() {
		int idSrc = 1;
		int idDest = 2;
		assertEquals(idSrc, 
				connectionRepository.findByConnectionId(idSrc, idDest).get().getSrcUser().getId());
	}

}
