package com.tipikae.paymybuddy.unit.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tipikae.paymybuddy.converters.IConverterUserToHomeDTO;
import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.ConverterException;

@SpringBootTest
class ConverterUserToHomeDTOTest {
	
	@Autowired
	private IConverterUserToHomeDTO converterUserToHomeDTO;

	@Test
	void convertToDTOReturnsHomeDTOWhenOk() throws ConverterException {
		BigDecimal balance = new BigDecimal(1000);
		Account account = new Account();
		account.setBalance(balance);
		User alice = new User();
		alice.setFirstname("Alice");
		alice.setLastname("ALICE");
		alice.setEmail("alice@alice.com");
		alice.setAccount(account);
		HomeDTO dto = converterUserToHomeDTO.convertToDTO(alice);
		assertEquals(balance, dto.getBalance());
	}

	@Test
	void convertToDTOThrowsConverterExceptionWhenEmptyField() {
		BigDecimal balance = new BigDecimal(1000);
		Account account = new Account();
		account.setBalance(balance);
		User alice = new User();
		alice.setFirstname("");
		alice.setLastname("ALICE");
		alice.setEmail("alice@alice.com");
		alice.setAccount(account);
		assertThrows(ConverterException.class,
				() -> converterUserToHomeDTO.convertToDTO(alice));
	}
}
