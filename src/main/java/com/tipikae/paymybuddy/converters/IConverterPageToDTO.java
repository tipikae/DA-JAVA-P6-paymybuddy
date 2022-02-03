/**
 * 
 */
package com.tipikae.paymybuddy.converters;

import org.springframework.data.domain.Page;

import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Generic converter Page to DTO.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IConverterPageToDTO<E, D> {

	/**
	 * Convert Page entities to Page DTO.
	 * @param entities
	 * @return Page<D>
	 * @throws ConverterException
	 */
	Page<D> convertToPageDTO(Page<E> entities) throws ConverterException;
}
