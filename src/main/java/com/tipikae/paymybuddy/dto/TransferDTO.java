package com.tipikae.paymybuddy.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Transfer DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class TransferDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Connections list.
	 */
	private List<ConnectionDTO> connections;
	
	/**
	 * Transactions list.
	 */
	private List<TransactionDTO> transactions;
}
