package com.tipikae.paymybuddy.converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Converter User to HomeDTO implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class ConverterUserToHomeDTO implements IConverterUserToHomeDTO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterUserToHomeDTO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HomeDTO convertToDTO(User user) throws ConverterException {
		HomeDTO homeDTO = new HomeDTO();
		try {
			homeDTO.setFirstname(user.getFirstname());
			homeDTO.setLastname(user.getLastname());
			homeDTO.setEmail(user.getEmail());
			homeDTO.setBalance(user.getAccount().getBalance());
		} catch (Exception e) {
			LOGGER.debug("ConverterException: " + e.getMessage());
			throw new ConverterException(e.getMessage());
		}
		
		return homeDTO;
	}

}
