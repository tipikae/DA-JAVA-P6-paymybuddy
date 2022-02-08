package com.tipikae.paymybuddy.unit.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tipikae.paymybuddy.converters.ConverterUserToProfileDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

@ExtendWith(SpringExtension.class)
class ConverterUserToProfileDTOTest {
	
	private ConverterUserToProfileDTO converterUserToProfileDTO
		= new ConverterUserToProfileDTO();

	@Test
	void convertToDTOReturnsProfileDTOWhenOk() throws ConverterException {
		Account account = new Account();
		account.setDateCreated(new Date());;
		User alice = new User();
		alice.setFirstname("Alice");
		alice.setLastname("ALICE");
		alice.setEmail("alice@alice.com");
		alice.setAccount(account);
		assertEquals("Alice", converterUserToProfileDTO.convertToDTO(alice).getFirstname());
	}

	@Test
	void convertToDTOThrowsConverterExceptionWhenEmptyField() {
		Account account = new Account();
		account.setDateCreated(new Date());;
		User alice = new User();
		alice.setFirstname("Alice");
		alice.setLastname("");
		alice.setEmail("alice@alice.com");
		alice.setAccount(account);
		assertThrows(ConverterException.class, () -> converterUserToProfileDTO.convertToDTO(alice));
	}
}
