package com.tipikae.paymybuddy.converters;

import java.util.ArrayList;
import java.util.List;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exception.ConverterException;

/**
 * Connection converter implementation.
 * @author tipikae
 * @version 1.0
 *
 */
public class ConnectionConverter implements IConnectionConverter {

	@Override
	public Connection convertToEntity(ConnectionDTO dto) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionDTO convertToDto(Connection entity) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConnectionDTO> convertToListDtos(List<Connection> entities) throws ConverterException {
		List<ConnectionDTO> connections = new ArrayList<>();
		for(Connection connection: entities) {
			User destUser = connection.getDestUser();
			ConnectionDTO connectionDTO = new ConnectionDTO();
			connectionDTO.setEmail(destUser.getEmail());
			connectionDTO.setFirstname(destUser.getFirstname());
			connectionDTO.setLastname(destUser.getLastname());
			connections.add(connectionDTO);
		}
		return connections;
	}

	@Override
	public List<Connection> convertToListEntities(List<ConnectionDTO> dtos) throws ConverterException {
		// TODO Auto-generated method stub
		return null;
	}

}
