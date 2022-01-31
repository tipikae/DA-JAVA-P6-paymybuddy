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
import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.ConnectionId;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConnectionForbiddenException;
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
		List<ConnectionDTO> others = getOthers(srcUser.getEmail());
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
	
	private List<ConnectionDTO> getOthers(String srcEmail) {
		List<ConnectionDTO> others = new ArrayList<>();
		List<User> users = userRepository.getPotentialFriends(srcEmail);
		for(User user: users) {
			ConnectionDTO otherDTO = new ConnectionDTO();
			otherDTO.setEmail(user.getEmail());
			otherDTO.setFirstname(user.getFirstname());
			otherDTO.setLastname(user.getLastname());
			others.add(otherDTO);
		}
		
		return others;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addConnection(String srcEmail, NewContactDTO newContactDTO) 
			throws UserNotFoundException, ConnectionForbiddenException {
		String destEmail = newContactDTO.getDestEmail();
		LOGGER.debug("Adding connection: source email=" + srcEmail + " dest email=" + destEmail);
		if(srcEmail.equals(destEmail)) {
			LOGGER.debug("AddConnection: source and dest are identical.");
			throw new ConnectionForbiddenException("Source and dest are identical.");
		}
		
		Optional<User> optionalSrc = userRepository.findByEmail(srcEmail);
		Optional<User> optionalDest = userRepository.findByEmail(destEmail);
		if(!optionalSrc.isPresent()) {
			LOGGER.debug("AddConnection: user src with email=" + srcEmail + " not found.");
			throw new UserNotFoundException("User not found.");
		}
		if(!optionalDest.isPresent()) {
			LOGGER.debug("AddConnection: user dest with email=" + destEmail + " not found.");
			throw new UserNotFoundException("User not found.");
		}
		
		ConnectionId connectionId = new ConnectionId(optionalSrc.get().getEmail(), optionalDest.get().getEmail());
		Connection connection = new Connection();
		connection.setId(connectionId);
		connection.setSrcUser(optionalSrc.get());
		connection.setDestUser(optionalDest.get());
		connectionRepository.save(connection);
	}

}
