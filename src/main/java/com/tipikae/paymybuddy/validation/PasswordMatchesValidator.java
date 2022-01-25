package com.tipikae.paymybuddy.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tipikae.paymybuddy.dto.UserDTO;

/**
 * PasswordMatchesValidator implementation of ConstraintValidator<A,T>
 * @author tipikae
 * @version 1.0
 *
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		UserDTO userDTO = (UserDTO) obj;
		return userDTO.getPassword().equals(userDTO.getConfirmedPassword());
	}

}
