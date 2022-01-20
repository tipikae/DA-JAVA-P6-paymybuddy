package com.tipikae.paymybuddy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.WebRequest;

import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.services.IUserService;

/**
 * User controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class UserController {

	@Autowired
	private IUserService userService;
	
	@GetMapping("/user/registration")
	public String getRegistrationForm(WebRequest request, Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return "registration";
		
	}
}
