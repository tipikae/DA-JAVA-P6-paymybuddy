package com.tipikae.paymybuddy.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.OperationController;
import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IConnectionService;
import com.tipikae.paymybuddy.services.IOperationService;
import com.tipikae.paymybuddy.util.IBreadcrumb;

@WebMvcTest(controllers = OperationController.class)
class OperationControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @MockBean
	private UserDetailsService userDetailsService;
	@MockBean
	private IOperationService operationService;
	@MockBean
	private IConnectionService connectionService;
	@MockBean
	private IBreadcrumb breadcrumb;
	
	private static NewOperationDTO rightDepOperationDTO;
	private static NewOperationDTO rightWitOperationDTO;
	private static NewOperationDTO wrongOperationDTO;
	
	@BeforeAll
	private static void setUp() {
		rightDepOperationDTO = new NewOperationDTO();
		rightDepOperationDTO.setTypeOperation("DEP");
		rightDepOperationDTO.setAmount(new BigDecimal(1000.0));
		
		rightWitOperationDTO = new NewOperationDTO();
		rightWitOperationDTO.setTypeOperation("WIT");
		rightWitOperationDTO.setAmount(new BigDecimal(500.0));
		
		wrongOperationDTO = new NewOperationDTO();
		wrongOperationDTO.setTypeOperation("DEP");
		wrongOperationDTO.setAmount(new BigDecimal(-1000.0));
	}
	
	@WithMockUser
	@Test
	void getTransactionsReturnsErrorWhenUserNotFoundException() throws Exception {
		doThrow(UserNotFoundException.class).when(operationService).getOperations(anyString(), anyInt(), anyInt());
		mockMvc.perform(get("/transaction"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}
	
	@WithMockUser
	@Test
	void getTransactionsReturnsTransferWhenOk() throws Exception {
		when(operationService.getOperations(anyString(), anyInt(), anyInt()))
			.thenReturn(new PageImpl(new ArrayList<>()));
		mockMvc.perform(get("/transaction"))
			.andExpect(status().isOk())
			.andExpect(view().name("transaction"));
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenOk() throws Exception {
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightDepOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/bank?success=Operation succeed."));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenInvalid() throws Exception {
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", wrongOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/bank?error=Amount must be positive. "));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenNotFound() throws Exception {
		doThrow(new UserNotFoundException("User not found."))
			.when(operationService).operation(anyString(), any(NewOperationDTO.class));
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightDepOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/bank?error=User not found."));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsHomeWhenForbidden() throws Exception {
		doThrow(new OperationForbiddenException("Amount can't be more than balance."))
			.when(operationService).operation(anyString(), any(NewOperationDTO.class));
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightWitOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/bank?error=Amount can't be more than balance."));
		
	}

}
