package com.tipikae.paymybuddy.controllers;

import java.security.Principal;

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

import com.tipikae.paymybuddy.dto.NewUserDTO;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.UserAlreadyExistException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
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

	/**
	 * UserService interface.
	 */
	@Autowired
	private IUserService userService;

	/**
	 * Mapping for display registration form.
	 * @param request
	 * @param model
	 * @return String
	 */
	@GetMapping("/user/registration")
	public String getRegistrationForm(
			WebRequest request,
			Model model) {
		LOGGER.debug("Get registration page.");
		
		NewUserDTO userDTO = new NewUserDTO();
		model.addAttribute("user", userDTO);
		return "registration";
		
	}

	/**
	 * Mapping for register a new user.
	 * @param userDTO
	 * @param errors
	 * @param request
	 * @return ModelAndView
	 */
	@PostMapping("/user/registration")
	public ModelAndView registerNewUser(
			  @ModelAttribute("user") @Valid NewUserDTO userDTO,
			  Errors errors,
			  HttpServletRequest request) {

		LOGGER.debug("Registering new user: {}", userDTO);
		if(errors.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			errors.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + ", "));
			LOGGER.debug("has errors:" + sb);
			return new ModelAndView("registration", "user", userDTO);
		}
		
		try {
			userService.registerNewUser(userDTO);
		} catch(UserAlreadyExistException e) {
			LOGGER.debug("Register: An user for that email already exists.");
			ModelAndView mav = new ModelAndView("registration", "user", userDTO);
			mav.addObject("message", "An user for that email already exists.");
	        return mav;
		} catch(Exception e) {
			LOGGER.debug("Register: Unable to register: " + e.getMessage());
			ModelAndView mav = new ModelAndView("registration", "user", userDTO);
			mav.addObject("message", "Unable to register.");
	        return mav;
		}

		LOGGER.debug("Registration succeed.");
		return new ModelAndView("registration_success", "", null);
	}
	
	/**
	 * Get home mapping.
	 * @param request
	 * @param model
	 * @return String
	 */
	@GetMapping("/home")
	public String getHome(HttpServletRequest request, Model model) {
		LOGGER.debug("getHome");
		Principal principal = request.getUserPrincipal();
		try {
			model.addAttribute("home", userService.getHomeDetails(principal.getName()));
		} catch (UserNotFoundException e) {
			LOGGER.debug("Get home: UserNotFoundException: " + e.getMessage());
			return "error/404";
		} catch (ConverterException e) {
			LOGGER.debug("Get home: DTO converter exception: " + e.getMessage());
			return "error/400";
		} catch(Exception e) {
			LOGGER.debug("Get home: Exception: " + e.getMessage());
			return "error/400";
		}
		
		return "home";
	}
	
	/**
	 * Get the user profile page.
	 * @param request
	 * @param model
	 * @return String
	 */
	@GetMapping("/profile")
	public String getProfile(HttpServletRequest request, Model model) {
		LOGGER.debug("Get profile");
		Principal principal = request.getUserPrincipal();
		try {
			model.addAttribute("profile", userService.getProfileDetails(principal.getName()));
		} catch (UserNotFoundException e) {
			LOGGER.debug("Get profile: UserNotFoundException: " + e.getMessage());
			return "error/404";
		} catch (ConverterException e) {
			LOGGER.debug("Get profile: DTO converter exception: " + e.getMessage());
			return "error/400";
		} catch(Exception e) {
			LOGGER.debug("Get profile: Exception: " + e.getMessage());
			return "error/400";
		}
		return "profile";
	}
	
	/**
	 * Get the bank page.
	 * @param request
	 * @param model
	 * @return String
	 */
	@GetMapping("/bank")
	public String getBank(HttpServletRequest request) {
		LOGGER.debug("Get bank");
		Principal principal = request.getUserPrincipal();
		try {
			userService.getBank(principal.getName());
		} catch (UserNotFoundException e) {
			LOGGER.debug("Get bank: UserNotFoundException: " + e.getMessage());
			return "error/404";
		} catch(Exception e) {
			LOGGER.debug("Get bank: Exception: " + e.getMessage());
			return "error/400";
		}
		return "bank";
	}
}
