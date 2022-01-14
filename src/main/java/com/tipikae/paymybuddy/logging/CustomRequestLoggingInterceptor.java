package com.tipikae.paymybuddy.logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * An implementation HandlerInterceptor for intercepting and logging requests.
 * @author tipikae
 * @version 1.0
 *
 */
@Component
public class CustomRequestLoggingInterceptor implements HandlerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomRequestLoggingInterceptor.class);

	/**
	 * {@inheritDoc}
	 * @return boolean
	 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String method = request.getMethod();
    	StringBuffer requestURL = request.getRequestURL();
    	String query = request.getQueryString();
        LOGGER.debug("preHandle => Method: {}, Request URL: {}, Parameters: {}", method, requestURL, query);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	int status = response.getStatus();
        LOGGER.debug("afterCompletion => Response Status: {}", status);
    }
}
