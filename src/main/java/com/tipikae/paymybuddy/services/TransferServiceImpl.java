package com.tipikae.paymybuddy.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.entities.Transfer;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.repositories.IOperationRepository;
import com.tipikae.paymybuddy.repositories.IUserRepository;

/**
 * Transfer Service implementation.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class TransferServiceImpl implements ITransferService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransferServiceImpl.class);
	
	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IOperationRepository operationRepository;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TransferDTO> getTransfers(String srcEmail) throws UserNotFoundException {
		LOGGER.debug("Getting transfers for " + srcEmail);
		Optional<User> optional = userRepository.findByEmail(srcEmail);
		if(!optional.isPresent()) {
			LOGGER.debug("GetProfile: user with email=" + srcEmail + " not found.");
			throw new UserNotFoundException("User not found.");
		}
		
		List<TransferDTO> transfersDTO = new ArrayList<>();
		List<Transfer> transfers = operationRepository.findTransfersByEmailSrc(srcEmail);
		for(Transfer transfer: transfers) {
			TransferDTO transferDTO = new TransferDTO();
			transferDTO.setConnection(transfer.getDestUser().getFirstname());
			transferDTO.setDescription(transfer.getDescription());
			transferDTO.setAmount(transfer.getAmount());
			transfersDTO.add(transferDTO);
		}
		
		return transfersDTO;
	}

}
