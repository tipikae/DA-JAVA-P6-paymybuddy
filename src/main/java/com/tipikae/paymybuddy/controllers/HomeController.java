package com.tipikae.paymybuddy.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tipikae.paymybuddy.dto.HomeDTO;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IHomeService;

/**
 * Home Controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IHomeService homeService;
	
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
			HomeDTO homeDTO = homeService.getHome(principal.getName());
			model.addAttribute("home", homeDTO);
		} catch (UserNotFoundException e) {
			LOGGER.debug("Get home: UserNotFoundException: " + e.getMessage());
			return "error/404";
		} catch(Exception e) {
			LOGGER.debug("Get home: Exception: " + e.getMessage());
			return "error/400";
		}
		
		return "home";
	}
}
