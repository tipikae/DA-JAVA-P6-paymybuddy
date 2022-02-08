package com.tipikae.paymybuddy.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Handler for redirection after login success.
 * @author tipikae
 * @version 1.0
 *
 */
public class MySimpleUrlAuthSuccessHandler implements AuthenticationSuccessHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MySimpleUrlAuthSuccessHandler.class);

	/**
	 * RedirectStrategy.
	 */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		handle(request, response, authentication);
        clearAuthenticationAttributes(request);
	}
	
	private void handle(
	        HttpServletRequest request,
	        HttpServletResponse response, 
	        Authentication authentication
	) throws IOException {

	    String targetUrl = determineTargetUrl(authentication);

	    if (response.isCommitted()) {
	        LOGGER.debug(
	                "Response has already been committed. Unable to redirect to "
	                        + targetUrl);
	        return;
	    }

	    redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private String determineTargetUrl(final Authentication authentication) {

	    Map<String, String> roleTargetUrlMap = new HashMap<>();
	    roleTargetUrlMap.put("ROLE_USER", "/home");

	    final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	    for (final GrantedAuthority grantedAuthority : authorities) {
	        String authorityName = grantedAuthority.getAuthority();
	        if(roleTargetUrlMap.containsKey(authorityName)) {
	            return roleTargetUrlMap.get(authorityName);
	        }
	    }

	    throw new IllegalStateException();
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session == null) {
	        return;
	    }
	    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
