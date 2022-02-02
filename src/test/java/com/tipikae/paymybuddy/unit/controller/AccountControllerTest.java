package com.tipikae.paymybuddy.unit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.tipikae.paymybuddy.controllers.AccountController;
import com.tipikae.paymybuddy.services.IAccountService;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @MockBean
	private UserDetailsService userDetailsService;
    @MockBean
    private IAccountService accountService;
}
