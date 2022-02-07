package com.tipikae.paymybuddy.unit.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.converters.IConverterListUserToConnectionDTO;
import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

@SpringBootTest
class ConverterListUserToConnectionDTOTest {
	
	@Autowired
	private IConverterListUserToConnectionDTO converterListUserToConnectionDTO;

	@Test
	void convertToListDTOsReturnsListWhenOK() throws ConverterException {
		User alice = new User();
		alice.setEmail("alice@alice.com");
		alice.setFirstname("Alice");
		alice.setLastname("ALICE");
		User bob = new User();
		bob.setEmail("bob@bob.com");
		bob.setFirstname("Bob");
		bob.setLastname("BOB");
		List<User> users = new ArrayList<>();
		users.add(alice);
		users.add(bob);
		List<ConnectionDTO> connections = converterListUserToConnectionDTO.convertToListDTOs(users);
		assertEquals(2, connections.size());
		assertEquals("BOB", connections.get(1).getLastname());
	}

}
