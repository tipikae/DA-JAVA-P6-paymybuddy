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

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.TransferDTO;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
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
		} catch (ConverterException e) {
			LOGGER.debug("DTO converter exception: " + e.getMessage());
			return "error/400";
		} catch (Exception e) {
			LOGGER.debug("Unable to process getTransfer: " + e.getMessage());
			return "error/400";
		}
		return ("transfer");
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
			return "redirect:/home?error=" + sb;
		}
		
		Principal principal = request.getUserPrincipal();
		try {
			operationService.operation(principal.getName(), operationDTO);
		} catch (UserNotFoundException e) {
			LOGGER.debug("User not found: " + e.getMessage());
			return "redirect:/home?error=" + e.getMessage();
		} catch (OperationForbiddenException e) {
			LOGGER.debug("Operation forbidden: " + e.getMessage());
			return "redirect:/home?error=" + e.getMessage();
		} catch (Exception e) {
			LOGGER.debug("Unable to process operation: " + e.getMessage());
			return "redirect:/home?error=" + e.getMessage();
		}
		
		return "redirect:/home?success=Operation succeed.";
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
			return "redirect:/home?error=" + sb;
		}
		
		Principal principal = request.getUserPrincipal();
		try {
			operationService.transfer(principal.getName(), newTransferDTO);
		} catch (UserNotFoundException e) {
			LOGGER.debug("User not found: " + e.getMessage());
			return "redirect:/transfer?error=User not found";
		} catch (OperationForbiddenException e) {
			LOGGER.debug("Operation forbidden: " + e.getMessage());
			return "redirect:/transfer?error=Operation forbidden.";
		} catch (Exception e) {
			LOGGER.debug("Unable to process operation: " + e.getMessage());
			return "redirect:/transfer?error=Unable to process transfer.";
		}
		
		return "redirect:/transfer?success=Operation succeed.";
		
	}
}
