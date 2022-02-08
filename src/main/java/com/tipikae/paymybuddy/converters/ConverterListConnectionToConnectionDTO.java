package com.tipikae.paymybuddy.converters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Connection converter to ConnectionDTO implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class ConverterListConnectionToConnectionDTO implements IConverterListConnectionToConnectionDTO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterListConnectionToConnectionDTO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ConnectionDTO> convertToListDTOs(List<Connection> connections) throws ConverterException {
		List<ConnectionDTO> connectionsDTO = new ArrayList<>();
		for(Connection connection: connections) {
			User destUser = connection.getDestUser();
			if(destUser.getEmail().equals("") || destUser.getFirstname().equals("") 
					|| destUser.getLastname().equals("")) {
				LOGGER.debug("ConverterException: Empty field: email=" + destUser.getEmail() 
					+ " firstname=" + destUser.getFirstname() + " lastname=" + destUser.getLastname());
				throw new ConverterException("Empty field.");
			}
			ConnectionDTO connectionDTO = new ConnectionDTO();
			connectionDTO.setEmail(destUser.getEmail());
			connectionDTO.setFirstname(destUser.getFirstname());
			connectionDTO.setLastname(destUser.getLastname());
			connectionsDTO.add(connectionDTO);
		}
		return connectionsDTO;
	}
}
