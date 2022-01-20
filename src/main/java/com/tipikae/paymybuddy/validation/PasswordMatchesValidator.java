package com.tipikae.paymybuddy.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tipikae.paymybuddy.dto.UserDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		UserDTO userDTO = (UserDTO) value;
		return userDTO.getPassword().equals(userDTO.getConfirmedPassword());
	}

}
