package com.tipikae.paymybuddy.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IConnectionService;
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
	@Autowired
	private IConnectionService connectionService;
	
	/**
	 * Get transactions page.
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("/transaction")
	public String getTransactions(HttpServletRequest request, Model model, HttpSession session) {
		LOGGER.debug("Get transactions");
		try {
			Principal principal = request.getUserPrincipal();
			session.setAttribute("page", "Transactions");
			model.addAttribute("connections", connectionService.getConnections(principal.getName()));
			model.addAttribute("operations", operationService.getOperations(principal.getName()));
		} catch (UserNotFoundException e) {
			LOGGER.debug("Get transactions: User not found: " + e.getMessage());
			return "error/404";
		} catch (ConverterException e) {
			LOGGER.debug("Get transactions: DTO converter exception: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Get transactions: Unable to process getTransfer: " + e.getMessage());
			return "error/400";
		}
		return ("transaction");
	}
	
	/**
	 * Save operation for deposit and withdrawal.
	 * @param request
	 * @param model
	 * @param typeOperation
	 * @param amount
	 * @return String
	 */
	@PostMapping("/saveOperation")
	public String saveOperation(
			@ModelAttribute("operation") @Valid NewOperationDTO operationDTO,
			Errors errors,
			HttpServletRequest request) {
		
		LOGGER.debug("Saving operation");
		if(errors.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			errors.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/bank?error=" + sb;
		}
		
		Principal principal = request.getUserPrincipal();
		try {
			operationService.operation(principal.getName(), operationDTO);
		} catch (UserNotFoundException e) {
			LOGGER.debug("Save operation: User not found: " + e.getMessage());
			return "redirect:/bank?error=User not found.";
		} catch (OperationForbiddenException e) {
			LOGGER.debug("Save operation: Operation forbidden: " + e.getMessage());
			return "redirect:/bank?error=Amount can't be more than balance.";
		} catch (Exception e) {
			LOGGER.debug("Save operation: Unable to process operation: " + e.getMessage());
			return "redirect:/bank?error=Unable to process operation.";
		}
		
		return "redirect:/bank?success=Operation succeed.";
	}
	
	@PostMapping("/saveTransfer")
	public String saveTransfer(
			@ModelAttribute("transfer") @Valid NewTransferDTO newTransferDTO,
			Errors errors,
			HttpServletRequest request) {
		
		LOGGER.debug("Saving transfer");
		if(errors.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			errors.getAllErrors().stream().forEach(e -> sb.append(e.getDefaultMessage() + " "));
			LOGGER.debug("has errors:" + sb);
			return "redirect:/transaction?error=" + sb;
		}
		
		Principal principal = request.getUserPrincipal();
		try {
			operationService.transfer(principal.getName(), newTransferDTO);
		} catch (UserNotFoundException e) {
			LOGGER.debug("Save transfer: User not found: " + e.getMessage());
			return "redirect:/transaction?error=User not found.";
		} catch (OperationForbiddenException e) {
			LOGGER.debug("Save transfer: Operation forbidden: " + e.getMessage());
			return "redirect:/transaction?error=Operation forbidden.";
		} catch (Exception e) {
			LOGGER.debug("Save transfer: Unable to process operation: " + e.getMessage());
			return "redirect:/transaction?error=Unable to process transfer.";
		}
		
		return "redirect:/transaction?success=Operation succeed.";
		
	}
}
