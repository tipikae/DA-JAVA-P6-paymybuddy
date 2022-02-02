package com.tipikae.paymybuddy.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * TransactionDTO
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class TransactionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Connection name;
	 */
	private String connection;
	
	/**
	 * Description.
	 */
	private String description;
	
	/**
	 * Amount
	 */
	private BigDecimal amount;
}
