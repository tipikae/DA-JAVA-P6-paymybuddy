package com.tipikae.paymybuddy.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * Breadcrumb DTO.
 * @author tipikae
 * @version 1.0
 *
 */
@Data
public class BreadcrumbDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Query.
	 */
	private String query;
	
	/**
	 * Label.
	 */
	private String label;
	
	/**
	 * Is current.
	 */
	private boolean isCurrent;
}
