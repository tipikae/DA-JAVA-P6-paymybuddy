package com.tipikae.paymybuddy.services;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.converters.IConverterListConnectionToConnectionDTO;
import com.tipikae.paymybuddy.converters.IConverterListTransferToTransactionDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IOperationRepository;

/**
 * Operation Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Transactional
@Service
public class OperationServiceImpl implements IOperationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	/**
	 * User Service.
	 */
	@Autowired
	private IUserService userService;
	
	/**
	 * OperationRepository.
	 */
	@Autowired
	private IOperationRepository operationRepository;
	
	/**
	 * Converter Connection list to ConnectionDTO list.
	 */
	private IConverterListConnectionToConnectionDTO converterConnectionToConnectionDTO;
	
	/**
	 * Converter Transfer list to TransactionDTO list.
	 */
	private IConverterListTransferToTransactionDTO converterTransferToTransactionDTO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TransferDTO getTransfersDetails(String srcEmail) 
			throws UserNotFoundException, ConverterException {
		LOGGER.debug("Getting transfer for " + srcEmail);
		User user = userService.isUserExists(srcEmail);
		
		TransferDTO transferDTO = new TransferDTO();
		transferDTO.setConnections(
				converterConnectionToConnectionDTO.convertToListDTOs(user.getConnections()));
		transferDTO.setTransactions(
				converterTransferToTransactionDTO.convertToListDTOs(
						operationRepository.findTransfersByIdSrc(user.getId())));
		
		return transferDTO;
	}

}
