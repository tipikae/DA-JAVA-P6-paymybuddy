package com.tipikae.paymybuddy.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.tipikae.paymybuddy.dto.UserDTO;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.services.IUserService;

/**
 * User controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	@GetMapping("/user/registration")
	public String getRegistrationForm(
			WebRequest request,
			Model model) {
		LOGGER.debug("Get registration page.");
		
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return "registration";
		
	}
	
	@PostMapping
	public ModelAndView registerNewUser(
			  @ModelAttribute("user") @Valid UserDTO userDTO,
			  Errors errors,
			  HttpServletRequest request) {
		
		if(errors.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			errors.getAllErrors().stream().forEach(e -> sb.append(e + ", "));
			LOGGER.debug("has errors:" + sb);
			return new ModelAndView("registration", "user", userDTO);
		}
		
		LOGGER.debug("Registering new user: {}", userDTO);
		
		// Register new user
		try {
			userService.registerNewUser(userDTO);
		} catch(UserAlreadyExistException e) {
			LOGGER.debug("An user for that email already exists.");
			ModelAndView mav = new ModelAndView("registration", "user", userDTO);
			mav.addObject("message", "An user for that email already exists.");
	        return mav;
		} catch(Exception e) {
			LOGGER.debug("Unable to register: " + e.getMessage());
			ModelAndView mav = new ModelAndView("registration", "user", userDTO);
			mav.addObject("message", "Unable to register.");
	        return mav;
		}
		
		// Auto-login new user
		try {
			request.login(userDTO.getEmail(), userDTO.getPassword());
			return new ModelAndView("user", "", null);
		} catch(ServletException e) {
			LOGGER.debug("Unable to login: ServletException: " + e.getMessage());
			return new ModelAndView("user", "", null);
		}
	}
}
