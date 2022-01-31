package com.tipikae.paymybuddy.converters;

import java.util.List;

import com.tipikae.paymybuddy.exception.ConverterException;

/**
 * Generic DTO converter.
 * @author tipikae
 * @version 1.0
 *
 */
public interface IConverter<E, D> {

	/**
	 * Convert a DTO to an entity.
	 * @param dto
	 * @return E
	 * @throws ConverterException
	 */
	E convertToEntity(D dto) throws ConverterException;
	
	/**
	 * Convert an entity to a DTO.
	 * @param entity
	 * @return D
	 */
	D convertToDto(E entity) throws ConverterException;
	
	/**
	 * Convert an entities list to a DTOs list.
	 * @param entities
	 * @return List<D>
	 * @throws ConverterException
	 */
	List<D> convertToListDtos(List<E> entities) throws ConverterException;
	
	/**
	 * Convert a DTOs list to an entities list.
	 * @param dtos
	 * @return List<E>
	 * @throws ConverterException
	 */
	List<E> convertToListEntities(List<D> dtos) throws ConverterException;
}
