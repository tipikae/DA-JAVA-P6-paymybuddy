package com.tipikae.paymybuddy.converters;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ConnectionDTO> convertToListDTOs(List<User> users) throws ConverterException {
		List<ConnectionDTO> connectionsDTO = new ArrayList<>();
		for(User user: users) {
			ConnectionDTO connectionDTO = new ConnectionDTO();
			connectionDTO.setEmail(user.getEmail());
			connectionDTO.setFirstname(user.getFirstname());
			connectionDTO.setLastname(user.getLastname());
			connectionsDTO.add(connectionDTO);
		}
		
		return connectionsDTO;
	}

}
