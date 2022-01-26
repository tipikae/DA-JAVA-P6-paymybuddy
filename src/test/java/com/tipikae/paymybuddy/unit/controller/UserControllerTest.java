package com.tipikae.paymybuddy.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.UserController;
import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.services.IUserService;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IUserService userService;
	
	private static UserDTO rightUserDTO;
	private static UserDTO wrongUserDTO;
	
	@BeforeAll
	private static void setUp() {
		rightUserDTO = new UserDTO();
		rightUserDTO.setEmail("bob@bob.com");
		rightUserDTO.setFirstname("Bob");
		rightUserDTO.setLastname("BOB");
		rightUserDTO.setPassword("bob");
		rightUserDTO.setConfirmedPassword("bob");
		
		wrongUserDTO = new UserDTO();
		wrongUserDTO.setEmail("bob@bob");
		wrongUserDTO.setFirstname("Bob");
		wrongUserDTO.setLastname("BOB");
		wrongUserDTO.setPassword("bob");
		wrongUserDTO.setConfirmedPassword("bob");
	}

	@Test
	void getRegistrationFormReturnsRegistration() throws Exception {
		mockMvc.perform(get("/user/registration"))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"));
	}

	@Test
	void registerNewUserReturnsSuccessWhenOk() throws Exception {
		when(userService.registerNewUser(any(UserDTO.class))).thenReturn(new User());
		mockMvc.perform(post("/user/registration")
				.flashAttr("user", rightUserDTO))
			.andExpect(status().isOk())
			.andExpect(view().name("registration_success"));
	}

	@Test
	void registerNewUserReturnsRegistrationWhenInvalid() throws Exception {
		when(userService.registerNewUser(any(UserDTO.class))).thenReturn(new User());
		mockMvc.perform(post("/user/registration")
				.flashAttr("user", wrongUserDTO))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"));
	}

	@Test
	void registerNewUserReturnsRegistrationWhenUserAlreadyExistException() throws Exception {
		doThrow(UserAlreadyExistException.class).when(userService).registerNewUser(any(UserDTO.class));
		mockMvc.perform(post("/user/registration")
				.flashAttr("user", rightUserDTO))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"));
	}
}
