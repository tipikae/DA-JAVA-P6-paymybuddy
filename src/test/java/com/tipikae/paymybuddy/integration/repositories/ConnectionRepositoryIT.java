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
		String emailSrc = "alice@alice.com";
		String emailDest = "bob@bob.com";
		assertEquals(emailSrc, 
				connectionRepository.findByConnectionId(emailSrc, emailDest).get().getSrcUser().getEmail());
	}

}
