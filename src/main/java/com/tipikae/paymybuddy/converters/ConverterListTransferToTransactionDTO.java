package com.tipikae.paymybuddy.converters;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tipikae.paymybuddy.dto.TransactionDTO;
import com.tipikae.paymybuddy.entities.Transfer;
import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Converter Transfer list to TransactionDTO list implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class ConverterListTransferToTransactionDTO implements IConverterListTransferToTransactionDTO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterListTransferToTransactionDTO.class);

	@Override
	public List<TransactionDTO> convertToListDTOs(List<Transfer> transfers) throws ConverterException {
		List<TransactionDTO> transactions = new ArrayList<>();
		
		try {
			for(Transfer transfer: transfers) {
				TransactionDTO transactionDTO = new TransactionDTO();
				transactionDTO.setConnection(transfer.getDestUser().getFirstname());
				transactionDTO.setDescription(transfer.getDescription());
				transactionDTO.setAmount(transfer.getAmount());
				transactions.add(transactionDTO);
			}
		} catch (Exception e) {
			LOGGER.debug("ConverterException: " + e.getMessage());
			throw new ConverterException(e.getMessage());
		}
		
		return transactions;
	}

}
