package com.tipikae.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Security controller.
 * @author tipikae
 * @version 1.0
 *
 */
@Controller
public class SecurityController {

	/**
	 * Login user.
	 * @return String
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	/**
	 * Access denied 403.
	 * @return String
	 */
	@GetMapping("/403")
	public String accessDenied() {
		return "error/403";
	}

	/**
	 * Not found 404.
	 * @return String
	 */
	@GetMapping("/404")
	public String notFound() {
		return "error/404";
	}

	/**
	 * Bad request 400
	 * @return String
	 */
	@GetMapping("/400")
	public String badRequest() {
		return "error/400";
	}
}
