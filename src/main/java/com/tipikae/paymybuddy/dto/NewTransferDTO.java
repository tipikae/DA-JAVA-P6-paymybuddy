package com.tipikae.paymybuddy.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Data;

/**
 * New Transfer DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class NewTransferDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Dest email.
	 */
	@NotBlank(message = "Connection must not be empty.")
	private String destEmail;
	
	/**
	 * Description.
	 */
	@NotBlank(message = "Description must not be empty.")
	private String description;
	
	/**
	 * Amount.
	 */
	@Positive(message = "Amount must be positive.")
	@Digits(integer = 10, fraction = 2, message = "Amount must be a decimal number.")
	private BigDecimal amount;
}
