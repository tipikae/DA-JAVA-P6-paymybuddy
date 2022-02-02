package com.tipikae.paymybuddy.converters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.entities.Operation;
import com.tipikae.paymybuddy.entities.Transfer;
import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Converter Operation list to OperationDTO list implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class ConverterListOperationToOperationDTO implements IConverterListOperationToOperationDTO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterListOperationToOperationDTO.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<OperationDTO> convertToListDTOs(List<Operation> operations) throws ConverterException {
		List<OperationDTO> dtos = new ArrayList<>();
		
		try {
			for(Operation operation: operations) {
				OperationDTO transactionDTO = new OperationDTO();
				transactionDTO.setType(operation.getClass().getSimpleName());
				transactionDTO.setAmount(operation.getAmount());
				
				if(operation instanceof Transfer) {
					Transfer transfer = (Transfer) operation;
					transactionDTO.setConnection(transfer.getDestUser().getFirstname());
					transactionDTO.setDescription(transfer.getDescription());
					transactionDTO.setFee(transfer.getFee());
				}
				
				dtos.add(transactionDTO);
			}
		} catch (Exception e) {
			LOGGER.debug("ConverterException: " + e.getMessage());
			throw new ConverterException(e.getMessage());
		}
		
		return dtos;
	}

}
