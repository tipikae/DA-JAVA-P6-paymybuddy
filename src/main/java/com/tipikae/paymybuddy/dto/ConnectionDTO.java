package com.tipikae.paymybuddy.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Connection DTO
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class ConnectionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Firstname.
	 */
	private String firstname;
	
	/**
	 * Lastname.
	 */
	private String lastname;
	
	/**
	 * Email.
	 */
	private String email;
}
