package com.tipikae.paymybuddy.converters;

import com.tipikae.paymybuddy.exceptions.ConverterException;

/**
 * Generic converter to DTO interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IConverterToDTO<E, D> {

	D convertToDTO(E entity) throws ConverterException;
}
