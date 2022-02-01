package com.tipikae.paymybuddy.converters;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProfileDTO convertToDTO(User user) throws ConverterException {
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setEmail(user.getEmail());
		profileDTO.setFirstname(user.getFirstname());
		profileDTO.setLastname(user.getLastname());
		profileDTO.setDateCreated(user.getAccount().getDateCreated());
		
		return profileDTO;
	}

}
