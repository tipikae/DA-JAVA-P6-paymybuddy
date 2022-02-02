package com.tipikae.paymybuddy.converters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Converter User list to ConnectionDTO list.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class ConverterListUserToConnectionDTO implements IConverterListUserToConnectionDTO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterListUserToConnectionDTO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ConnectionDTO> convertToListDTOs(List<User> users) throws ConverterException {
		List<ConnectionDTO> connectionsDTO = new ArrayList<>();
		try {
			for(User user: users) {
				ConnectionDTO connectionDTO = new ConnectionDTO();
				connectionDTO.setEmail(user.getEmail());
				connectionDTO.setFirstname(user.getFirstname());
				connectionDTO.setLastname(user.getLastname());
				connectionsDTO.add(connectionDTO);
			}
		} catch (Exception e) {
			LOGGER.debug("ConverterException: " + e.getMessage());
			throw new ConverterException(e.getMessage());
		}
		
		return connectionsDTO;
	}

}
