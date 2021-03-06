package com.tipikae.paymybuddy.controllers;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tipikae.paymybuddy.dto.NewOperationDTO;
import com.tipikae.paymybuddy.dto.NewTransferDTO;
import com.tipikae.paymybuddy.dto.OperationDTO;
import com.tipikae.paymybuddy.exceptions.BreadcrumbException;
import com.tipikae.paymybuddy.exceptions.ConverterException;
import com.tipikae.paymybuddy.exceptions.OperationForbiddenException;
import com.tipikae.paymybuddy.exceptions.UserNotFoundException;
import com.tipikae.paymybuddy.services.IConnectionService;
import com.tipikae.paymybuddy.services.IOperationService;
import com.tipikae.paymybuddy.util.IBreadcrumb;

/**
 * Operation Controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class OperationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OperationController.class);
	
	/**
	 * Operation service interface.
	 */
	@Autowired
	private IOperationService operationService;
	
	/**
	 * Connection service interface.
	 */
	@Autowired
	private IConnectionService connectionService;
	
	/**
	 * Breadcrumb interface.
	 */
	@Autowired
	private IBreadcrumb breadcrumb;
	
	/**
	 * Get transactions page.
	 * @param request
	 * @param model
	 * @param session
	 * @param page
	 * @param size
	 * @return String
	 */
	@GetMapping("/transaction")
	public String getTransactions(HttpServletRequest request, Model model, HttpSession session, 
			@RequestParam(name="page", defaultValue="1")int page, 
			@RequestParam(name="size", defaultValue="5")int size) {
		LOGGER.debug("Get transactions");
		try {
			Principal principal = request.getUserPrincipal();
			Page<OperationDTO> pageOperations = 
					operationService.getOperations(principal.getName(), page - 1, size);
			
			model.addAttribute("connections", connectionService.getConnections(principal.getName()));
			model.addAttribute("operations", pageOperations);

			if(pageOperations.getTotalPages() > 1) {
				List<Integer> pages = IntStream.rangeClosed(1, pageOperations.getTotalPages())
						.boxed()
						.collect(Collectors.toList());
				model.addAttribute("pages", pages);
			}
			
			session.setAttribute("breadcrumb", breadcrumb.getBreadCrumb("/transaction", "Transactions"));
		} catch (BreadcrumbException e) {
			LOGGER.debug("Get transactions: BreadcrumbException: " + e.getMessage());
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
	 * @param operationDTO
	 * @param errors
	 * @param request
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
	
	/**
	 * Save transfer.
	 * @param newTransferDTO
	 * @param errors
	 * @param request
	 * @return String
	 */
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
			return "redirect:/transaction?error=" + e.getMessage();
		} catch (Exception e) {
			LOGGER.debug("Save transfer: Unable to process operation: " + e.getMessage());
			return "redirect:/transaction?error=Unable to process transfer.";
		}
		
		return "redirect:/transaction?success=Operation succeed.";
		
	}
}
