package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConnectionForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IConnectionRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.ConnectionServiceImpl;
import com.tipikae.paymybuddy.services.IUserService;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {
	
	@Mock
	private IUserService userService;
	@Mock
	private IUserRepository userRepository;
	@Mock
	private IConnectionRepository connectionRepository;
	
	@InjectMocks
	private ConnectionServiceImpl connectionService;

	@Test
	void getConnectionsReturnsListWhenEmailFound() throws UserNotFoundException {
		User alice = new User();
		alice.setEmail("alice@alice.com");
		User bob = new User();
		bob.setEmail("bob@bob.com");
		Connection connection = new Connection();
		connection.setSrcUser(alice);
		connection.setDestUser(bob);
		List<Connection> connections = new ArrayList<>();
		connections.add(connection);
		alice.setConnections(connections);
		when(userService.isUserExists(anyString())).thenReturn(alice);
		assertEquals("bob@bob.com", connectionService.getConnectionsByEmail("alice@alice.com").getConnections().get(0).getEmail());
	}

	@Test
	void getConnectionsThrowsUserNotFoundExceptionWhenEmailNotFound() throws UserNotFoundException {
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, () -> connectionService.getConnectionsByEmail("bob@bob.com"));
	}
	
	@Test
	void addConnectionCallsSaveWhenOk() throws UserNotFoundException, ConnectionForbiddenException {
		User alice = new User();
		alice.setEmail("alice@alice.com");
		User bob = new User();
		bob.setEmail("bob@bob.com");
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("bob@bob.com");
		when(userService.isUserExists(anyString())).thenReturn(alice, bob);
		connectionService.addConnection("alice@alice.com", newContactDTO);
		verify(connectionRepository, Mockito.times(1)).save(any(Connection.class));
	}
	
	@Test
	void addConnectionThrowsConnectionForbiddenExceptionWhenEmailsEquals() {
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("alice@alice.com");
		assertThrows(ConnectionForbiddenException.class, 
				() -> connectionService.addConnection("alice@alice.com", newContactDTO));
	}
	
	@Test
	void addConnectionThrowsUserNotFoundExceptionWhenSrcNotFound() throws UserNotFoundException {
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("bob@bob.com");
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, 
				() -> connectionService.addConnection("alice@alice.com", newContactDTO));
	}
	
	@Test
	void addConnectionThrowsUserNotFoundExceptionWhenDestNotFound() throws UserNotFoundException {
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("bob@bob.com");
		when(userService.isUserExists(anyString())).thenThrow(UserNotFoundException.class);
		assertThrows(UserNotFoundException.class, 
				() -> connectionService.addConnection("alice@alice.com", newContactDTO));
	}

}
