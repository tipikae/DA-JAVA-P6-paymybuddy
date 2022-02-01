package com.tipikae.paymybuddy.converters;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HomeDTO convertToDTO(User user) throws ConverterException {
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setEmail(user.getEmail());
		homeDTO.setBalance(user.getAccount().getBalance());
		
		return homeDTO;
	}

}
