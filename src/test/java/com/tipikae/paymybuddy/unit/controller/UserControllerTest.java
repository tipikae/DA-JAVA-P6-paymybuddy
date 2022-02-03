package com.tipikae.paymybuddy.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.UserController;
import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.dto.NewUserDTO;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IUserService;
import com.tipikae.paymybuddy.util.IBreadcrumb;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

    @MockBean
	private UserDetailsService userDetailsService;
	@MockBean
	private IUserService userService;
	@MockBean
	private IBreadcrumb breadcrumb;
	
	private static NewUserDTO rightUserDTO;
	private static NewUserDTO wrongUserDTO;
	
	@BeforeAll
	private static void setUp() {
		rightUserDTO = new NewUserDTO();
		rightUserDTO.setEmail("bob@bob.com");
		rightUserDTO.setFirstname("Bob");
		rightUserDTO.setLastname("BOB");
		rightUserDTO.setPassword("bob");
		rightUserDTO.setConfirmedPassword("bob");
		
		wrongUserDTO = new NewUserDTO();
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
		when(userService.registerNewUser(any(NewUserDTO.class))).thenReturn(new User());
		mockMvc.perform(post("/user/registration")
				.flashAttr("user", rightUserDTO))
			.andExpect(status().isOk())
			.andExpect(view().name("registration_success"));
	}

	@Test
	void registerNewUserReturnsRegistrationWhenInvalid() throws Exception {
		when(userService.registerNewUser(any(NewUserDTO.class))).thenReturn(new User());
		mockMvc.perform(post("/user/registration")
				.flashAttr("user", wrongUserDTO))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"));
	}

	@Test
	void registerNewUserReturnsRegistrationWhenUserAlreadyExistException() throws Exception {
		doThrow(UserAlreadyExistException.class).when(userService).registerNewUser(any(NewUserDTO.class));
		mockMvc.perform(post("/user/registration")
				.flashAttr("user", rightUserDTO))
			.andExpect(status().isOk())
			.andExpect(view().name("registration"));
	}
	
	@WithMockUser
	@Test
	void getProfileReturnsProfileWhenFound() throws Exception {
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setEmail("bob@bob.com");
		profileDTO.setFirstname("bob");
		profileDTO.setLastname("Bob");
		profileDTO.setDateCreated(new Date());
		when(userService.getProfileDetails(anyString())).thenReturn(profileDTO);
		mockMvc.perform(get("/profile"))
			.andExpect(status().isOk())
			.andExpect(view().name("profile"));
	}
	
	@WithMockUser
	@Test
	void getProfileReturns404WhenNotFound() throws Exception {
		doThrow(UserNotFoundException.class).when(userService).getProfileDetails(anyString());
		mockMvc.perform(get("/profile"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}

	@WithMockUser
	@Test
	void getHomeReturnsHomeWhenFound() throws Exception {
		HomeDTO homeDTO = new HomeDTO();
		homeDTO.setBalance(new BigDecimal(1000.0));
		when(userService.getHomeDetails(anyString())).thenReturn(homeDTO);
		mockMvc.perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
	}

	@WithMockUser
	@Test
	void getHomeReturns404WhenNotFound() throws Exception {
		doThrow(UserNotFoundException.class).when(userService).getHomeDetails(anyString());
		mockMvc.perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(view().name("error/404"));
	}
}
