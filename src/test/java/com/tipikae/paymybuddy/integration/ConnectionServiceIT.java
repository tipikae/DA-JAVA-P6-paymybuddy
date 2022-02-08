package com.tipikae.paymybuddy.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.exceptions.ConnectionForbiddenException;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IConnectionService;

@SpringBootTest
class ConnectionServiceIT {
	
	@Autowired
	private IConnectionService connectionService;

	@Test
	void getConnectionsReturnsListWhenOk() throws UserNotFoundException, ConverterException {
		ConnectionDTO aliceBob = new ConnectionDTO();
		aliceBob.setEmail("bob@bob.com");
		aliceBob.setFirstname("Bob");
		aliceBob.setLastname("BOB");
		List<ConnectionDTO> connections = connectionService.getConnections("alice@alice.com");
		assertEquals(true, connections.contains(aliceBob));
	}

	@Test
	void getConnectionsThrowsUserNotFoundExceptionWhenEmailNotFound() {
		assertThrows(UserNotFoundException.class, () -> connectionService.getConnections("test@test.com"));
	}

	@Transactional
	@Test
	void addConnectionReturnsConnectionWhenOk() throws UserNotFoundException, ConnectionForbiddenException {
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("alice@alice.com");
		assertEquals("alice@alice.com", 
				connectionService.addConnection("hector@hector.com", newContactDTO).getDestUser().getEmail());
	}

	@Test
	void addConnectionThrowsConnectionForbiddenExceptionWhenEmailsEqual() {
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("test@test.com");
		assertThrows(ConnectionForbiddenException.class, 
				() -> connectionService.addConnection("test@test.com", newContactDTO));
	}

	@Test
	void addConnectionThrowsUserNotFoundExceptionWhenEmailNotFound() {
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("test@test.com");
		assertThrows(UserNotFoundException.class, 
				() -> connectionService.addConnection("pouet@pouet.com", newContactDTO));
	}
}
