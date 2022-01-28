package com.tipikae.paymybuddy.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.OperationController;
import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.exception.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IOperationService;

@WebMvcTest(controllers = OperationController.class)
class OperationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IOperationService operationService;
	
	private static OperationDTO rightDepOperationDTO;
	private static OperationDTO rightWitOperationDTO;
	private static OperationDTO wrongOperationDTO;
	
	@BeforeAll
	private static void setUp() {
		rightDepOperationDTO = new OperationDTO();
		rightDepOperationDTO.setTypeOperation("DEP");
		rightDepOperationDTO.setAmount(1000.0);
		
		rightWitOperationDTO = new OperationDTO();
		rightWitOperationDTO.setTypeOperation("WIT");
		rightWitOperationDTO.setAmount(500.0);
		
		wrongOperationDTO = new OperationDTO();
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
			.when(operationService).deposit(anyString(), any(OperationDTO.class));
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightDepOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/home?error=User not found."));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenForbidden() throws Exception {
		doThrow(new OperationForbiddenException("Amount can't be more than balance."))
			.when(operationService).withdrawal(anyString(), any(OperationDTO.class));
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightWitOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/home?error=Amount can't be more than balance."));
		
	}

}