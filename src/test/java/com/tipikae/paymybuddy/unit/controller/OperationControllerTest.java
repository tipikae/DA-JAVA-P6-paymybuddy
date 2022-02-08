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
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.exceptions.BreadcrumbException;
import com.tipikae.paymybuddy.exceptions.ConverterException;
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
	private static NewTransferDTO rightTransferDTO;
	private static NewTransferDTO wrongTransferDTO;
	
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
		
		rightTransferDTO = new NewTransferDTO();
		rightTransferDTO.setAmount(new BigDecimal(1000));
		rightTransferDTO.setDescription("test");
		rightTransferDTO.setDestEmail("bob@bob.com");
		
		wrongTransferDTO = new NewTransferDTO();
		wrongTransferDTO.setAmount(new BigDecimal(-1000));
		wrongTransferDTO.setDescription("test");
		wrongTransferDTO.setDestEmail("bob@bob.com");
	}
	
	@WithMockUser
	@Test
	void getTransactionsReturnsTransactionWhenOk() throws Exception {
		when(operationService.getOperations(anyString(), anyInt(), anyInt()))
			.thenReturn(new PageImpl(new ArrayList<>()));
		mockMvc.perform(get("/transaction"))
			.andExpect(status().isOk())
			.andExpect(view().name("transaction"));
	}
	
	@WithMockUser
	@Test
	void getTransactionsReturnsTransactionWhenBreadcrumbException() throws Exception {
		when(operationService.getOperations(anyString(), anyInt(), anyInt()))
			.thenReturn(new PageImpl(new ArrayList<>()));
		doThrow(BreadcrumbException.class).when(breadcrumb).getBreadCrumb(anyString(), anyString());
		mockMvc.perform(get("/transaction"))
			.andExpect(status().isOk())
			.andExpect(view().name("transaction"));
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
	void getTransactionsReturnsErrorWhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(operationService).getOperations(anyString(), anyInt(), anyInt());
		mockMvc.perform(get("/transaction"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsBankWhenOk() throws Exception {
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightDepOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/bank?success=Operation succeed."));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsBankWhenInvalid() throws Exception {
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", wrongOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/bank?error=Amount must be positive. "));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsBankWhenNotFound() throws Exception {
		doThrow(new UserNotFoundException("User not found."))
			.when(operationService).operation(anyString(), any(NewOperationDTO.class));
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightDepOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/bank?error=User not found."));
		
	}
	
	@WithMockUser
	@Test
	void saveDepositOperationRedirectsBankWhenForbidden() throws Exception {
		doThrow(new OperationForbiddenException("Amount can't be more than balance."))
			.when(operationService).operation(anyString(), any(NewOperationDTO.class));
		mockMvc.perform(post("/saveOperation")
				.flashAttr("operation", rightWitOperationDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/bank?error=Amount can't be more than balance."));
		
	}
	
	@WithMockUser
	@Test
	void saveTransferOperationRedirectsTransactionWhenOk() throws Exception {
		mockMvc.perform(post("/saveTransfer")
				.flashAttr("transfer", rightTransferDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/transaction?success=Operation succeed."));
		
	}
	
	@WithMockUser
	@Test
	void saveTransferOperationRedirectsTransactionWhenInvalid() throws Exception {
		mockMvc.perform(post("/saveTransfer")
				.flashAttr("transfer", wrongTransferDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/transaction?error=Amount must be positive. "));
		
	}
	
	@WithMockUser
	@Test
	void saveTransferOperationRedirectsTransactionWhenNotFound() throws Exception {
		doThrow(new UserNotFoundException("User not found."))
			.when(operationService).transfer(anyString(), any(NewTransferDTO.class));
		mockMvc.perform(post("/saveTransfer")
				.flashAttr("transfer", rightTransferDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/transaction?error=User not found."));
		
	}
	
	@WithMockUser
	@Test
	void saveTransferOperationRedirectsTransactionWhenForbidden() throws Exception {
		doThrow(new OperationForbiddenException("Amount can't be more than balance."))
			.when(operationService).transfer(anyString(), any(NewTransferDTO.class));
		mockMvc.perform(post("/saveTransfer")
				.flashAttr("transfer", rightTransferDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/transaction?error=Amount can't be more than balance."));
		
	}

}
