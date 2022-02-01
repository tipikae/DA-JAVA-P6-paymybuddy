package com.tipikae.paymybuddy.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tipikae.paymybuddy.dto.TransferDTO;
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
	 * Get transfers page.
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/transfer")
	public String getTransfer(HttpServletRequest request, Model model) {
		LOGGER.debug("Get transfer");
		try {
			Principal principal = request.getUserPrincipal();
			TransferDTO transferDTO = operationService.getTransfersDetails(principal.getName());
			model.addAttribute("transfer", transferDTO);
		} catch (UserNotFoundException e) {
			LOGGER.debug("User not found: " + e.getMessage());
			return "error/404";
		} catch (Exception e) {
			LOGGER.debug("Unable to process getTransfer: " + e.getMessage());
			return "error/400";
		}
		return ("transfer");
	}
}
