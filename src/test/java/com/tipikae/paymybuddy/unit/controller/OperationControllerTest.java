package com.tipikae.paymybuddy.unit.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.OperationController;
import com.tipikae.paymybuddy.dto.TransferDTO;
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
