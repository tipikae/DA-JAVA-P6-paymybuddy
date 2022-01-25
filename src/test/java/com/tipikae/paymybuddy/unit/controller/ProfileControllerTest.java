package com.tipikae.paymybuddy.unit.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.ProfileController;
import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IProfileService;

@WebMvcTest(controllers = ProfileController.class)
class ProfileControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private IProfileService profileService;
	
	@WithMockUser
	@Test
	void getProfileReturns200WhenFound() throws Exception {
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setEmail("bob@bob.com");
		profileDTO.setFirstname("bob");
		profileDTO.setLastname("Bob");
		profileDTO.setDateCreated(new Date());
		when(profileService.getProfile(anyString())).thenReturn(profileDTO);
		mockMvc.perform(get("/profile"))
			.andExpect(status().isOk());
	}
	
	@WithMockUser
	@Test
	void getProfileReturns404WhenNotFound() throws Exception {
		doThrow(UserNotFoundException.class).when(profileService).getProfile(anyString());
		mockMvc.perform(get("/profile"))
			.andExpect(status().is(404));
	}
	
	@WithMockUser
	@Test
	void getProfileReturns400WhenException() throws Exception {
		doThrow(Exception.class).when(profileService).getProfile(anyString());
		mockMvc.perform(get("/profile"))
			.andExpect(status().is(400));
	}

}
