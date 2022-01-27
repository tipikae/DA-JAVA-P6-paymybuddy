package com.tipikae.paymybuddy.unit.controller;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.ConnectionController;
import com.tipikae.paymybuddy.dto.ContactDTO;
import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.exception.ConnectionForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IConnectionService;

@WebMvcTest(controllers = ConnectionController.class)
class ConnectionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IConnectionService connectionService;
	
	private static NewContactDTO rightNewContactDTO;
	private static NewContactDTO wrongNewContactDTO;
	
	@BeforeAll
	private static void setUp() {
		rightNewContactDTO = new NewContactDTO();
		rightNewContactDTO.setDestEmail("bob@bob.com");
		wrongNewContactDTO = new NewContactDTO();
	}

	@WithMockUser
	@Test
	void getContactReturnsContactPageWhenOk() throws Exception {
		ContactDTO contactDTO = new ContactDTO();
		contactDTO.setConnections(new ArrayList<>());
		contactDTO.setOthers(new ArrayList<>());
		when(connectionService.getContact(anyString())).thenReturn(contactDTO);
		mockMvc.perform(get("/contact"))
			.andExpect(status().isOk())
			.andExpect(view().name("contact"));
	}

	@WithMockUser
	@Test
	void getContactReturnsErrorWhenEmailNotFound() throws Exception {
		doThrow(UserNotFoundException.class).when(connectionService).getContact(anyString());
		mockMvc.perform(get("/contact"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}
	
	@WithMockUser
	@Test
	void addContactReturnsSuccessWhenOk() throws Exception {
		mockMvc.perform(post("/saveContact")
				.flashAttr("newContactDTO", rightNewContactDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/contact?success=New connection succeed."));
	}
	
	@WithMockUser
	@Test
	void addContactReturnsErrorWhenInvalid() throws Exception {
		mockMvc.perform(post("/saveContact")
				.flashAttr("newContactDTO", wrongNewContactDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/contact?error=Must not be empty. "));
	}
	
	@WithMockUser
	@Test
	void addContactReturnsErrorWhenEmailNotFound() throws Exception {
		doThrow(UserNotFoundException.class).when(connectionService).addConnection(anyString(), anyString());
		mockMvc.perform(post("/saveContact")
				.flashAttr("newContactDTO", rightNewContactDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/contact?error=User not found."));
	}
	
	@WithMockUser
	@Test
	void addContactReturnsErrorWhenEmailEquals() throws Exception {
		doThrow(ConnectionForbiddenException.class).when(connectionService).addConnection(anyString(), anyString());
		mockMvc.perform(post("/saveContact")
				.flashAttr("newContactDTO", rightNewContactDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/contact?error=Connection forbidden."));
	}

}
