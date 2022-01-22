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

	/**
	 * Email.
	 */
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;

	/**
	 * Firstname.
	 */
	@NotNull
	@NotEmpty
	private String firstname;

	/**
	 * Lastname.
	 */
	@NotNull
	@NotEmpty
	private String lastname;

	/**
	 * Password.
	 */
	@NotNull
	@NotEmpty
	private String password;

	/**
	 * ConfirmedPassword.
	 */
	@NotNull
	@NotEmpty
	private String confirmedPassword;
}
