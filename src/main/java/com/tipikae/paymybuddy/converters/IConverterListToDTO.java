/**
 * 
 */
package com.tipikae.paymybuddy.converters;

import java.util.List;

import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Generic converter to List DTO interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IConverterListToDTO<E, D> {

	/**
	 * Convert an entities list to a dtos list.
	 * @param dtos
	 * @return List<E>
	 * @throws ConverterException
	 */
	List<D> convertToListDTOs(List<E> entities) throws ConverterException;
}
