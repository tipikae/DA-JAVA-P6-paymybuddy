package com.tipikae.paymybuddy.unit.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.converters.IConverterListConnectionToConnectionDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

@SpringBootTest
class ConverterListConnectionToConnectionDTOTest {
	
	@Autowired
	private IConverterListConnectionToConnectionDTO converterListConnectionToConnectionDTO;

	@Test
	void convertToListDTOs() throws ConverterException {
		User alice = new User();
		alice.setEmail("alice@alice.com");
		alice.setFirstname("Alice");
		alice.setLastname("ALICE");
		User bob = new User();
		bob.setEmail("bob@bob.com");
		bob.setFirstname("Bob");
		bob.setLastname("BOB");
		Connection connection = new Connection();
		connection.setSrcUser(alice);
		connection.setDestUser(bob);
		List<Connection> connections = new ArrayList<>();
		connections.add(connection);
		assertEquals(bob.getEmail(), 
				converterListConnectionToConnectionDTO.convertToListDTOs(connections).get(0).getEmail());
	}

}
