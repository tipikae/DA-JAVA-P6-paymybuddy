package com.tipikae.paymybuddy.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Converter User to ProfileDTO implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class ConverterUserToProfileDTO implements IConverterUserToProfileDTO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterUserToProfileDTO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileDTO convertToDTO(User user) throws ConverterException {
		ProfileDTO profileDTO = new ProfileDTO();
		try {
			profileDTO.setEmail(user.getEmail());
			profileDTO.setFirstname(user.getFirstname());
			profileDTO.setLastname(user.getLastname());
			profileDTO.setDateCreated(user.getAccount().getDateCreated());
		} catch (Exception e) {
			LOGGER.debug("ConverterException: " + e.getMessage());
			throw new ConverterException(e.getMessage());
		}
		
		return profileDTO;
	}

}
