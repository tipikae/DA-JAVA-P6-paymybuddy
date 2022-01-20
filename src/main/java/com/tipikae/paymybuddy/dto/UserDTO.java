package com.tipikae.paymybuddy.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.tipikae.paymybuddy.validation.PasswordMatches;

import lombok.Data;

/**
 * DTO class for User
 * @author tipikae
 * @version 1.0
 *
 */
@Data
@PasswordMatches
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	@Email
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
