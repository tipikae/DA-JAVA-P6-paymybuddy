package com.tipikae.paymybuddy.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.dto.ContactDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IConnectionRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;

/**
 * Connection Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class ConnectionServiceImpl implements IConnectionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionServiceImpl.class);
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IConnectionRepository connectionRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactDTO getContact(String srcEmail) throws UserNotFoundException {
		LOGGER.debug("Getting connections: source email=" + srcEmail);
		Optional<User> optional = userRepository.findByEmail(srcEmail);
		if(!optional.isPresent()) {
			LOGGER.debug("GetConnections: user with email=" + srcEmail + " not found.");
			throw new UserNotFoundException("User not found.");
		}
		
		User srcUser = optional.get();
		List<ConnectionDTO> connections = getConnections(srcUser);
		List<ConnectionDTO> others = getOthers(srcUser);
		ContactDTO contactDTO = new ContactDTO();
		contactDTO.setConnections(connections);
		contactDTO.setOthers(others);
		
		return contactDTO;
	}
	
	private List<ConnectionDTO> getConnections(User srcUser) {
		List<ConnectionDTO> connections = new ArrayList<>();
		for(Connection connection: srcUser.getConnections()) {
			User destUser = connection.getDestUser();
			ConnectionDTO connectionDTO = new ConnectionDTO();
			connectionDTO.setEmail(destUser.getEmail());
			connectionDTO.setFirstname(destUser.getFirstname());
			connectionDTO.setLastname(destUser.getLastname());
			connections.add(connectionDTO);
		}
		return connections;
	}
	
	private List<ConnectionDTO> getOthers(User srcUser) {
		List<ConnectionDTO> others = new ArrayList<>();
		List<User> existing = new ArrayList<>();
		for(Connection connection: srcUser.getConnections()) {
			existing.add(connection.getDestUser());
		}
		existing.add(srcUser);
		List<User> users = userRepository.findAll();
		users.removeAll(existing);
		for(User user: users) {
			ConnectionDTO otherDTO = new ConnectionDTO();
			otherDTO.setEmail(user.getEmail());
			otherDTO.setFirstname(user.getFirstname());
			otherDTO.setLastname(user.getLastname());
			others.add(otherDTO);
		}
		
		return others;
	}

}
