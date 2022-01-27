package com.tipikae.paymybuddy.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Contact DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class ContactDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ConnectionDTO existing list.
	 */
	private List<ConnectionDTO> connections;
	
	/**
	 * ConnectionDTO others list.
	 */
	private List<ConnectionDTO> others;
}
