package com.tipikae.paymybuddy.unit.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.converters.IConverterUserToProfileDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

@SpringBootTest
class ConverterUserToProfileDTOTest {
	
	@Autowired
	private IConverterUserToProfileDTO converterUserToProfileDTO;

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

}
