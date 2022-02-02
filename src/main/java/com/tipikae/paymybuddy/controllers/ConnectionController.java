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

import com.tipikae.paymybuddy.dto.NewContactDTO;
import com.tipikae.paymybuddy.exceptions.ConnectionForbiddenException;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IConnectionService;

/**
 * Connection controller
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class ConnectionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionController.class);
	
	@Autowired
	private IConnectionService connectionService;
	
	/**
	 * Get contact mapping.
	 * @param request
	 * @param model
	 * @return @return
	 */
	@GetMapping("/contact")
	public String getContact(HttpServletRequest request, Model model) {
		LOGGER.debug("Get contact");
		try {
			Principal principal = request.getUserPrincipal();
			model.addAttribute("connections", connectionService.getConnections(principal.getName()));
			model.addAttribute("others", connectionService.getPotentialConnections(principal.getName()));
		} catch (UserNotFoundException e) {
			LOGGER.debug("Get contact: User not found exception: " + e.getMessage());
			return "error/404";
		} catch (ConverterException e) {
			LOGGER.debug("Get contact: DTO converter exception: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Get contact: Unable to process contact: " + e.getMessage());
			return "error/400";
		}
		
		return "contact";
	}
	
	@PostMapping("/saveContact")
	public String addContact(
			@ModelAttribute("contact") @Valid NewContactDTO newContactDTO,
			Errors errors,
			HttpServletRequest request) {
		
		LOGGER.debug("Add new connection");
		if(errors.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			errors.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/contact?error=" + sb;
		}
		
		try {
			Principal principal = request.getUserPrincipal();
			connectionService.addConnection(principal.getName(), newContactDTO);
		} catch (UserNotFoundException e) {
			LOGGER.debug("Save contact: User not found: " + e.getMessage());
			return "redirect:/contact?error=User not found.";
		} catch (ConnectionForbiddenException e) {
			LOGGER.debug("Save contact: Connection forbidden : " + e.getMessage());
			return "redirect:/contact?error=Connection forbidden.";
		} catch (Exception e) {
			LOGGER.debug("Save contact: Unable to process new connection : " + e.getMessage());
			return "redirect:/contact?error=Unable to process new connection.";
		}

		return "redirect:/contact?success=New connection succeed.";
	}
}
