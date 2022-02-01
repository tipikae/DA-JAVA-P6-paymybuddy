package com.tipikae.paymybuddy.services;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.converters.IConverterListConnectionToConnectionDTO;
import com.tipikae.paymybuddy.converters.IConverterListUserToConnectionDTO;
import com.tipikae.paymybuddy.dto.ContactDTO;
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
	 * Converter User to ConnectionDTO.
	 */
	@Autowired
	private IConverterListUserToConnectionDTO converterUserToConnectionDTO;

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
	public ContactDTO getConnectionsDetails(String srcEmail) throws UserNotFoundException, ConverterException {
		LOGGER.debug("Getting connections: source email=" + srcEmail);
		User user = userService.isUserExists(srcEmail);
		
		ContactDTO contactDTO = new ContactDTO();
		contactDTO.setConnections(
				converterConnectionToConnectionDTO.convertToListDTOs(user.getConnections()));
		contactDTO.setOthers(
				converterUserToConnectionDTO.convertToListDTOs(connectionRepository.getPotentialConnections(user.getId())));
		
		return contactDTO;
	}
}
