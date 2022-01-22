package com.tipikae.paymybuddy.integration.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.repositories.IRoleRepository;

@SpringBootTest
class RoleRepositoryIT {

	@Autowired
	private IRoleRepository roleRepository;
	
	@Test
	void testCount() {
		assertEquals(2, roleRepository.count());
	}

}
