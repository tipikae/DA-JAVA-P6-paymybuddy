package com.tipikae.paymybuddy.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tipikae.paymybuddy.dto.ProfileDTO;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IProfileService;

/**
 * Profile controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class ProfileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	private IProfileService profileService;
	
	/**
	 * Get the user profile.
	 * @param request
	 * @param model
	 * @return String
	 */
	@GetMapping("/profile")
	public String getProfile(HttpServletRequest request, Model model) {
		LOGGER.debug("Get profile");
		Principal principal = request.getUserPrincipal();
		try {
			ProfileDTO profile = profileService.getProfile(principal.getName());
			model.addAttribute("profile", profile);
		} catch (UserNotFoundException e) {
			LOGGER.debug("Get profile: UserNotFoundException: " + e.getMessage());
			return "error/404";
		} catch(Exception e) {
			LOGGER.debug("Get profile: Exception: " + e.getMessage());
			return "error/400";
		}
		return "profile";
		
	}
}
