package com.tipikae.paymybuddy.unit.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exception.ConnectionForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IConnectionRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;
import com.tipikae.paymybuddy.services.ConnectionServiceImpl;

@ExtendWith(MockitoExtension.class)
class ConnectionServiceTest {
	
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
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(alice));
		assertEquals("bob@bob.com", connectionService.getContact("alice@alice.com").getConnections().get(0).getEmail());
	}

	@Test
	void getConnectionsThrowsUserNotFoundExceptionWhenEmailNotFound() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> connectionService.getContact("bob@bob.com"));
	}
	
	@Test
	void addConnectionCallsSaveWhenOk() throws UserNotFoundException, ConnectionForbiddenException {
		User alice = new User();
		alice.setEmail("alice@alice.com");
		User bob = new User();
		bob.setEmail("bob@bob.com");
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("bob@bob.com");
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(alice), Optional.of(bob));
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
	void addConnectionThrowsUserNotFoundExceptionWhenSrcNotFound() {
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("bob@bob.com");
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty(), Optional.of(new User()));
		assertThrows(UserNotFoundException.class, 
				() -> connectionService.addConnection("alice@alice.com", newContactDTO));
	}
	
	@Test
	void addConnectionThrowsUserNotFoundExceptionWhenDestNotFound() {
		NewContactDTO newContactDTO = new NewContactDTO();
		newContactDTO.setDestEmail("bob@bob.com");
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()), Optional.empty());
		assertThrows(UserNotFoundException.class, 
				() -> connectionService.addConnection("alice@alice.com", newContactDTO));
	}

}
