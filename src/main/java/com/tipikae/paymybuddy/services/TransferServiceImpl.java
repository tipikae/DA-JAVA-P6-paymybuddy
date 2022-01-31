package com.tipikae.paymybuddy.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.dto.ConnectionDTO;
import com.tipikae.paymybuddy.dto.TransactionDTO;
import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.entities.Connection;
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
	public TransferDTO getTransfer(String srcEmail) throws UserNotFoundException {
		LOGGER.debug("Getting transfer for " + srcEmail);
		Optional<User> optional = userRepository.findByEmail(srcEmail);
		if(!optional.isPresent()) {
			LOGGER.debug("GetTransfer: user with email=" + srcEmail + " not found.");
			throw new UserNotFoundException("User not found.");
		}
		
		List<ConnectionDTO> connections = getConnections(optional.get());
		List<TransactionDTO> transactions = getTransactions(srcEmail);
		TransferDTO transferDTO = new TransferDTO();
		transferDTO.setConnections(connections);
		transferDTO.setTransactions(transactions);
		
		return transferDTO;
	}
	
	private List<ConnectionDTO> getConnections(User srcUser) {
		List<ConnectionDTO> connections = new ArrayList<>();
		for(Connection connection: srcUser.getConnections()) {
			User destUser = connection.getDestUser();
			ConnectionDTO connectionDTO = new ConnectionDTO();
			connectionDTO.setEmail(destUser.getEmail());
			connectionDTO.setFirstname(destUser.getFirstname());
			connectionDTO.setLastname(destUser.getLastname());
			connections.add(connectionDTO);
		}
		
		return connections;
	}
	
	private List<TransactionDTO> getTransactions(String srcEmail) {
		List<TransactionDTO> transactions = new ArrayList<>();
		List<Transfer> transfers = operationRepository.findTransfersByEmailSrc(srcEmail);
		for(Transfer transfer: transfers) {
			TransactionDTO transactionDTO = new TransactionDTO();
			transactionDTO.setConnection(transfer.getDestUser().getFirstname());
			transactionDTO.setDescription(transfer.getDescription());
			transactionDTO.setAmount(transfer.getAmount());
			transactions.add(transactionDTO);
		}
		
		return transactions;
	}

}
