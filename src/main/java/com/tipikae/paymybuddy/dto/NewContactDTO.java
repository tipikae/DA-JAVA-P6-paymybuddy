package com.tipikae.paymybuddy.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * New Contact DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class NewContactDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Destination email.
	 */
	@NotBlank(message = "Must not be empty.")
	private String destEmail;
}
