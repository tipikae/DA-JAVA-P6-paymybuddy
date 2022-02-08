package com.tipikae.paymybuddy.unit.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tipikae.paymybuddy.converters.ConverterListUserToConnectionDTO;
import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

@ExtendWith(SpringExtension.class)
class ConverterListUserToConnectionDTOTest {
	
	private ConverterListUserToConnectionDTO converterListUserToConnectionDTO 
		= new ConverterListUserToConnectionDTO();

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
		List<User> users = new ArrayList<>();
		users.add(allFields);
		users.add(notAllFields);
		assertThrows(ConverterException.class, 
				() -> converterListUserToConnectionDTO.convertToListDTOs(users));
	}
}
