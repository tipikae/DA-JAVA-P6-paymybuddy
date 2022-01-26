package com.tipikae.paymybuddy.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Profile DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class ProfileDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Email.
	 */
	private String email;
	
	/**
	 * Firstname.
	 */
	private String firstname;
	
	/**
	 * Lastname.
	 */
	private String lastname;
	
	/**
	 * Registration date.
	 */
	private Date dateCreated;
}
