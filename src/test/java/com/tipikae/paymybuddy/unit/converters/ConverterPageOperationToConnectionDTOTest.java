package com.tipikae.paymybuddy.unit.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.tipikae.paymybuddy.converters.IConverterPageOperationToOperationDTO;
import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.entities.Deposit;
import com.tipikae.paymybuddy.entities.Operation;
import com.tipikae.paymybuddy.entities.Transfer;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.entities.Withdrawal;
import com.tipikae.paymybuddy.exceptions.ConverterException;

@SpringBootTest
class ConverterPageOperationToConnectionDTOTest {
	
	@Autowired
	private IConverterPageOperationToOperationDTO converterPageOperationToOperationDTO;

	@Value("${paymybuddy.rate}")
	private BigDecimal rate;
	
	@Test
	void convertToPageDTOReturnsPageOperationDTOWhenOk() throws ConverterException {
		BigDecimal amountDeposit = new BigDecimal(1000);
		Deposit deposit = new Deposit();
		deposit.setAmount(amountDeposit);
		Withdrawal withdrawal = new Withdrawal();
		withdrawal.setAmount(new BigDecimal(2000));
		Transfer transfer = new Transfer();
		BigDecimal amountTransfer = new BigDecimal(3000);
		User alice = new User();
		alice.setFirstname("Alice");
		transfer.setAmount(amountTransfer);
		transfer.setDescription("test");
		transfer.setFee(amountTransfer.multiply(rate));
		transfer.setDestUser(alice);
		List<Operation> operations = new ArrayList<>();
		operations.add(deposit);
		operations.add(withdrawal);
		operations.add(transfer);
		Page<Operation> page = new PageImpl<>(operations);
		Page<OperationDTO> dtos = converterPageOperationToOperationDTO.convertToPageDTO(page);
		assertEquals(3, dtos.getNumberOfElements());
		assertEquals(amountDeposit, dtos.getContent().get(0).getAmount());
	}

}
