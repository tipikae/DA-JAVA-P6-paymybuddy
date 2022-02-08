package com.tipikae.paymybuddy.converters;

import java.time.ZoneId;

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
		if(user.getEmail().equals("") || user.getFirstname().equals("") 
				|| user.getLastname().equals("")) {
			LOGGER.debug("ConverterException: Empty field: email=" + user.getEmail() 
				+ " firstname=" + user.getFirstname() + " lastname=" + user.getLastname());
			throw new ConverterException("Empty field.");
		}
		profileDTO.setEmail(user.getEmail());
		profileDTO.setFirstname(user.getFirstname());
		profileDTO.setLastname(user.getLastname());
		profileDTO.setDateCreated(user.getAccount().getDateCreated()
				.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate());
		
		return profileDTO;
	}

}
