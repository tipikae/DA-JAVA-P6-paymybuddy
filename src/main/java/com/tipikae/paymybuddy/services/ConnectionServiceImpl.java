package com.tipikae.paymybuddy.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.converters.IConverterListConnectionToConnectionDTO;
import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.ConnectionId;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConnectionForbiddenException;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IConnectionRepository;

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
	private IUserService userService;
	
	/**
	 * Connection repository.
	 */
	@Autowired
	private IConnectionRepository connectionRepository;
	
	/**
	 * Converter Connection to ConnectionDTO.
	 */
	@Autowired
	private IConverterListConnectionToConnectionDTO converterConnectionToConnectionDTO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ConnectionDTO> getConnections(String srcEmail) 
			throws UserNotFoundException, ConverterException {
		LOGGER.debug("Getting connections for " + srcEmail);
		User user = userService.isUserExists(srcEmail);
		
		return converterConnectionToConnectionDTO.convertToListDTOs(user.getConnections());
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
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
		User userDest = userService.isUserExists(destEmail);
		
		ConnectionId connectionId = new ConnectionId(userSrc.getId(), userDest.getId());
		Connection connection = new Connection();
		connection.setId(connectionId);
		connection.setSrcUser(userSrc);
		connection.setDestUser(userDest);
		connectionRepository.save(connection);
		
		return connection;
	}
}
