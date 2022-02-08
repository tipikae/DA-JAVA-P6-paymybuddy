package com.tipikae.paymybuddy.unit.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tipikae.paymybuddy.converters.ConverterListConnectionToConnectionDTO;
import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

@ExtendWith(SpringExtension.class)
class ConverterListConnectionToConnectionDTOTest {
	
	private ConverterListConnectionToConnectionDTO converterListConnectionToConnectionDTO
		= new ConverterListConnectionToConnectionDTO();

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
		Connection connection = new Connection();
		connection.setSrcUser(alice);
		connection.setDestUser(bob);
		List<Connection> connections = new ArrayList<>();
		connections.add(connection);
		List<ConnectionDTO> dtos = converterListConnectionToConnectionDTO.convertToListDTOs(connections);
		assertEquals(1, dtos.size());
		assertEquals(bob.getEmail(), dtos.get(0).getEmail());
	}
	
	@Test
	void convertToListDTOsThrowsConverterExceptionWhenEmptyField() {
		User allFields = new User();
		allFields.setEmail("alice@alice.com");
		allFields.setFirstname("Alice");
		allFields.setLastname("ALICE");
		User notAllFields = new User();
		notAllFields.setEmail("");
		notAllFields.setFirstname("Bob");
		notAllFields.setLastname("BOB");
		Connection connection = new Connection();
		connection.setSrcUser(allFields);
		connection.setDestUser(notAllFields);
		List<Connection> connections = new ArrayList<>();
		connections.add(connection);
		assertThrows(ConverterException.class, 
				() -> converterListConnectionToConnectionDTO.convertToListDTOs(connections));
	}

}
