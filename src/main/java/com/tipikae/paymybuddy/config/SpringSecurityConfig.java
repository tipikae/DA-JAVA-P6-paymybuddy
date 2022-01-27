package com.tipikae.paymybuddy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.tipikae.paymybuddy.services.UserDetailsServiceImpl;

/**
 * A class configuration for Spring Security.
 * @author tipikae
 * @version 1.0
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	//@Autowired
	private UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override 
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
            .successHandler(myAuthenticationSuccessHandler())
			.and()
			.logout().deleteCookies("JSESSIONID")
			.and()
			.rememberMe().key("MySecretRMKey");
		http.authorizeRequests()
			.antMatchers("/home", "/profile", "/transfer", "contact", "/saveContact", "/saveOperation")
			.hasRole("USER");
		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");
		http.authorizeRequests().antMatchers("/user/registration").permitAll();
		http.exceptionHandling().accessDeniedPage("/403");
	}

	/**
	 * Set the PasswordEncoder type.
	 * @return a PasswordEncoder.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Set the redirection when login succeed, based on role.
	 * @return MySimpleUrlAuthSuccessHandler
	 */
	@Bean
	public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
	    return new MySimpleUrlAuthSuccessHandler();
	}
}
