package com.tipikae.paymybuddy.unit.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.SecurityController;

@WebMvcTest(controllers = SecurityController.class)
class SecurityControllerTest {

    @Autowired
	private MockMvc mockMvc;
    
    @MockBean
	private UserDetailsService userDetailsService;

	@Test
	void login() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}

	@Test
	void accessDenied() throws Exception {
		mockMvc.perform(get("/403"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/403"));
	}

	@Test
	void notFound() throws Exception {
		mockMvc.perform(get("/404"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@Test
	void badRequest() throws Exception {
		mockMvc.perform(get("/400"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/400"));
	}

}
