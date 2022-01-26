package com.tipikae.paymybuddy.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.tipikae.paymybuddy.exception.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IOperationService;

/**
 * Operation Controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class OperationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationController.class);
	
	@Autowired
	private IOperationService operationService;
	
	/**
	 * Save operation for deposit and withdrawal.
	 * @param request
	 * @param model
	 * @param typeOperation
	 * @param amount
	 * @return String
	 */
	@PostMapping("/operation")
	public String saveOperation(HttpServletRequest request, Model model, String typeOperation, double amount) {
		LOGGER.debug("Saving operation");
		Principal principal = request.getUserPrincipal();
		try {
			switch(typeOperation) {
				case "DEP":
					operationService.deposit(principal.getName(), amount);
					break;
				case "WIT":
					operationService.withdrawal(principal.getName(), amount);
					break;
			}
		} catch (UserNotFoundException e) {
			model.addAttribute("error", e);
			return "redirect:/home&error=" + e.getMessage();
		} catch (OperationForbiddenException e) {
			model.addAttribute("error", e);
			return "redirect:/home&error=" + e.getMessage();
		}
		
		return "redirect:/home";
	}
	
	@PostMapping("/transfer")
	public String saveTransfer() {
		LOGGER.debug("Saving transfer");
		return null;
		
	}
}
