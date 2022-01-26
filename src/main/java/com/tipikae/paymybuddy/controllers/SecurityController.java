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

	@GetMapping("/home")
	public String home() {
		return "user";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping("/403")
	public String accessDenied() {
		return "error/403";
	}

	@GetMapping("/404")
	public String notFound() {
		return "error/404";
	}

	@GetMapping("/400")
	public String badRequest() {
		return "error/400";
	}
}
