package com.tipikae.paymybuddy.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.tipikae.paymybuddy.validation.PasswordMatches;
import com.tipikae.paymybuddy.validation.ValidEmail;

import lombok.Data;

/**
 * DTO class for User
 * @author tipikae
 * @version 1.0
 *
 */
@PasswordMatches
@Data
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String firstname;
	
	@NotNull
	@NotEmpty
	private String lastname;
	
	@NotNull
	@NotEmpty
	private String password;
	
	@NotNull
	@NotEmpty
	private String confirmedPassword;
}
