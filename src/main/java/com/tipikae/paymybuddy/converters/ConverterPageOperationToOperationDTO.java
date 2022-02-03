/**
 * 
 */
package com.tipikae.paymybuddy.converters;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.entities.Operation;
import com.tipikae.paymybuddy.entities.Transfer;
import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Converter Page Operation to Page OperationDTO implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class ConverterPageOperationToOperationDTO implements IConverterPageOperationToOperationDTO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterPageOperationToOperationDTO.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<OperationDTO> convertToPageDTO(Page<Operation> operations) throws ConverterException {
		try {
			Page<OperationDTO> pageDTO = operations.map(new Function<Operation, OperationDTO>() {

				@Override
				public OperationDTO apply(Operation operation) {
					OperationDTO operationDTO = new OperationDTO();
					operationDTO.setType(operation.getClass().getSimpleName());
					operationDTO.setAmount(operation.getAmount());
					
					if(operation instanceof Transfer) {
						Transfer transfer = (Transfer) operation;
						operationDTO.setConnection(transfer.getDestUser().getFirstname());
						operationDTO.setDescription(transfer.getDescription());
						operationDTO.setFee(transfer.getFee());
					}
					return operationDTO;
				}
				
			});
			
			return pageDTO;
		} catch (Exception e) {
			LOGGER.debug("convertToPageDTO exception: " + e.getMessage());
			throw new ConverterException("convertToPageDTO failed");
		}
	}

}
