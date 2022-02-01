package com.tipikae.paymybuddy.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.OperationController;
import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IOperationService;

@WebMvcTest(controllers = OperationController.class)
class OperationControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @MockBean
	private UserDetailsService userDetailsService;
	@MockBean
	private IOperationService operationService;
	
	private static NewOperationDTO rightDepOperationDTO;
	private static NewOperationDTO rightWitOperationDTO;
	private static NewOperationDTO wrongOperationDTO;
	
	@BeforeAll
	private static void setUp() {
		rightDepOperationDTO = new NewOperationDTO();
		rightDepOperationDTO.setTypeOperation("DEP");
		rightDepOperationDTO.setAmount(1000.0);
		
		rightWitOperationDTO = new NewOperationDTO();
		rightWitOperationDTO.setTypeOperation("WIT");
		rightWitOperationDTO.setAmount(500.0);
		
		wrongOperationDTO = new NewOperationDTO();
		wrongOperationDTO.setTypeOperation("DEP");
		wrongOperationDTO.setAmount(-1000.0);
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenOk() throws Exception {
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightDepOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/home?success=Operation succeed."));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenInvalid() throws Exception {
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", wrongOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/home?error=Amount must be positive. "));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenNotFound() throws Exception {
		doThrow(new UserNotFoundException("User not found."))
			.when(operationService).deposit(anyString(), any(NewOperationDTO.class));
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightDepOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/home?error=User not found."));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenForbidden() throws Exception {
		doThrow(new OperationForbiddenException("Amount can't be more than balance."))
			.when(operationService).withdrawal(anyString(), any(NewOperationDTO.class));
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightWitOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/home?error=Amount can't be more than balance."));
		
	}
	
	@WithMockUser
	@Test
	void getTransferReturnsErrorWhenUserNotFoundException() throws Exception {
		doThrow(UserNotFoundException.class).when(operationService).getTransfersDetails(anyString());
		mockMvc.perform(get("/transfer"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}
	
	@WithMockUser
	@Test
	void getTransferReturnsTransferWhenOk() throws Exception {
		TransferDTO transferDTO = new TransferDTO();
		transferDTO.setConnections(new ArrayList<>());
		transferDTO.setTransactions(new ArrayList<>());
		when(operationService.getTransfersDetails(anyString())).thenReturn(transferDTO);
		mockMvc.perform(get("/transfer"))
			.andExpect(status().isOk())
			.andExpect(view().name("transfer"));
	}

}
