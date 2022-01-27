package com.tipikae.paymybuddy.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Data;

/**
 * Operation DTO.
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
	@NotBlank(message = "Type must not be empty.")
	private String typeOperation;
	
	/**
	 * Operation amount.
	 */
	@Positive(message = "Amount must be positive.")
	private double amount;
}
