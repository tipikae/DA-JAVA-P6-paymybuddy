package com.tipikae.paymybuddy.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Home DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class HomeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Email.
	 */
	private String email;
	
	/**
	 * Balance.
	 */
	private double balance;

}
