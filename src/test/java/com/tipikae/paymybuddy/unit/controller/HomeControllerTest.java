package com.tipikae.paymybuddy.unit.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.HomeController;
import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IHomeService;

@WebMvcTest(controllers = HomeController.class)
class HomeControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IHomeService homeService;

	@WithMockUser
	@Test
	void getHomeReturnsHomeWhenFound() throws Exception {
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setBalance(1000.0);
		when(homeService.getHome(anyString())).thenReturn(homeDTO);
		mockMvc.perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
	}

	@WithMockUser
	@Test
	void getHomeReturns404WhenNotFound() throws Exception {
		doThrow(UserNotFoundException.class).when(homeService).getHome(anyString());
		mockMvc.perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

}
