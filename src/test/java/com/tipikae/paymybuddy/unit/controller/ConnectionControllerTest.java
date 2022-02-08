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

import com.tipikae.paymybuddy.controllers.ConnectionController;
import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.exceptions.BreadcrumbException;
import com.tipikae.paymybuddy.exceptions.ConnectionForbiddenException;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IConnectionService;
import com.tipikae.paymybuddy.services.IUserService;
import com.tipikae.paymybuddy.util.IBreadcrumb;

@WebMvcTest(controllers = ConnectionController.class)
class ConnectionControllerTest {

    @Autowired
	private MockMvc mockMvc;
    
    @MockBean
	private UserDetailsService userDetailsService;
	@MockBean
	private IConnectionService connectionService;
	@MockBean
	private IUserService userService;
	@MockBean
	private IBreadcrumb breadcrumb;
	
	private static NewContactDTO rightNewContactDTO;
	private static NewContactDTO wrongNewContactDTO;
	
	@BeforeAll
	private static void setUp() {
		rightNewContactDTO = new NewContactDTO();
		rightNewContactDTO.setDestEmail("bob@bob.com");
		wrongNewContactDTO = new NewContactDTO();
		wrongNewContactDTO.setDestEmail("");
	}

	@WithMockUser
	@Test
	void getContactReturnsContactPageWhenOk() throws Exception {
		when(connectionService.getConnections(anyString())).thenReturn(new ArrayList<>());
		when(userService.getPotentialConnections(anyString())).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/contact"))
			.andExpect(status().isOk())
			.andExpect(view().name("contact"));
	}

	@WithMockUser
	@Test
	void getContactReturnsErrorWhenEmailNotFound() throws Exception {
		doThrow(UserNotFoundException.class).when(connectionService).getConnections(anyString());
		mockMvc.perform(get("/contact"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@WithMockUser
	@Test
	void getContactReturnsContactWhenBreadcrumbException() throws Exception {
		doThrow(BreadcrumbException.class).when(breadcrumb).getBreadCrumb(anyString(), anyString());
		mockMvc.perform(get("/contact"))
			.andExpect(status().isOk())
			.andExpect(view().name("contact"));
	}

	@WithMockUser
	@Test
	void getContactReturnsErrorWhenConverterException() throws Exception {
		doThrow(ConverterException.class).when(userService).getPotentialConnections(anyString());
		mockMvc.perform(get("/contact"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}
	
	@WithMockUser
	@Test
	void addContactReturnsSuccessWhenOk() throws Exception {
		mockMvc.perform(post("/saveContact")
				.flashAttr("contact", rightNewContactDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/contact?success=New connection succeed."));
	}
	
	@WithMockUser
	@Test
	void addContactReturnsErrorWhenInvalid() throws Exception {
		mockMvc.perform(post("/saveContact")
				.flashAttr("contact", wrongNewContactDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/contact?error=Must not be empty. "));
	}
	
	@WithMockUser
	@Test
	void addContactReturnsErrorWhenEmailNotFound() throws Exception {
		doThrow(UserNotFoundException.class)
			.when(connectionService).addConnection(anyString(), any(NewContactDTO.class));
		mockMvc.perform(post("/saveContact")
				.flashAttr("contact", rightNewContactDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/contact?error=User not found."));
	}
	
	@WithMockUser
	@Test
	void addContactReturnsErrorWhenEmailEquals() throws Exception {
		doThrow(ConnectionForbiddenException.class)
			.when(connectionService).addConnection(anyString(), any(NewContactDTO.class));
		mockMvc.perform(post("/saveContact")
				.flashAttr("contact", rightNewContactDTO))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/contact?error=Connection forbidden."));
	}

}
