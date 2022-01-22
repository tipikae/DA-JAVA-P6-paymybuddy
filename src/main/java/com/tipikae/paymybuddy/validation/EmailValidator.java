package com.tipikae.paymybuddy.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * EmailValidator implementation of ConstraintValidator<A,T>.
 * @author tipikae
 * @version 1.0
 *
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
	
	/**
	 * Pattern object.
	 */
	private Pattern pattern;
	
	/**
	 * Matcher object.
	 */
    private Matcher matcher;
    
    /**
     * Email pattern.
     */
    private static final String EMAIL_PATTERN = 
    		"^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*" +
    		"@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*" +
    		"\\.([A-Za-z]{2,})$"; 

    /**
     * {@inheritDoc}
     * @param email
     * @param {{@inheritDoc}}
     */
	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		return (validateEmail(email));
	}

	private boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
