package com.tipikae.paymybuddy.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
@Transactional
@Service
public class ConnectionServiceImpl implements IConnectionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionServiceImpl.class);
	
	@Autowired
	private IUserService userService;
	
	/**
	 * User repository.
	 */
	@Autowired
	private IUserRepository userRepository;
	
	/**
	 * Connection repository.
	 */
	@Autowired
	private IConnectionRepository connectionRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Connection addConnection(String srcEmail, NewContactDTO newContactDTO) 
			throws UserNotFoundException, ConnectionForbiddenException {
		String destEmail = newContactDTO.getDestEmail();
		LOGGER.debug("Adding connection: source email=" + srcEmail + " dest email=" + destEmail);
		if(srcEmail.equals(destEmail)) {
			LOGGER.debug("AddConnection: source and dest are identical.");
			throw new ConnectionForbiddenException("Source and dest are identical.");
		}
		
		User userSrc = userService.isUserExists(srcEmail);
		User userDest = userService.isUserExists(srcEmail);
		
		ConnectionId connectionId = new ConnectionId(userSrc.getId(), userDest.getId());
		Connection connection = new Connection();
		connection.setId(connectionId);
		connection.setSrcUser(userSrc);
		connection.setDestUser(userDest);
		connectionRepository.save(connection);
		
		return connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContactDTO getConnectionsDetails(String srcEmail) throws UserNotFoundException {
		LOGGER.debug("Getting connections: source email=" + srcEmail);
		User user = userService.isUserExists(srcEmail);
		
		List<ConnectionDTO> connections = getConnections(user);
		List<ConnectionDTO> others = getOthers(user);
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
		List<User> users = userRepository.getPotentialConnections(srcUser.getId());
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
