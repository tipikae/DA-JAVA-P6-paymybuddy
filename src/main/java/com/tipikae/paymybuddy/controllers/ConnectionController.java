package com.tipikae.paymybuddy.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tipikae.paymybuddy.dto.ContactDTO;
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
		Principal principal = request.getUserPrincipal();
		try {
			ContactDTO contactDTO= connectionService.getContact(principal.getName());
			model.addAttribute("connections", contactDTO.getConnections());
			model.addAttribute("others", contactDTO.getOthers());
		} catch (UserNotFoundException e) {
			LOGGER.debug("User not found exception: " + e.getMessage());
			return "error/404";
		} catch (Exception e) {
			LOGGER.debug("Unable to process contact: " + e.getMessage());
			return "error/400";
		}
		
		return "contact";
	}
}
