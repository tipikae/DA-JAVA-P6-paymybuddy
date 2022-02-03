package com.tipikae.paymybuddy.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * Operation DTO
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class OperationDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Operation type.
	 */
	private String type;

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
	
	/**
	 * Fee.
	 */
	private BigDecimal fee;
}
