package com.tipikae.paymybuddy.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A class configuration for Spring Security.
 * @author tipikae
 * @version 1.0
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private DataSource datasource;

	/**
	 * {@inheritDoc}
	 * @param {@inheritDoc}
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(datasource)
			.usersByUsernameQuery("SELECT email as principal, password as credential, active FROM users WHERE email = ?")
			.authoritiesByUsernameQuery("SELECT email_user as principal, role FROM users_roles WHERE email_user = ?")
			.rolePrefix("ROLE_")
			.passwordEncoder(passwordEncoder());
	}
	
	/**
	 * {@inheritDoc}
	 * @param {@inheritDoc}
	 */
	@Override 
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin().loginPage("/login").usernameParameter("email").and().logout();
		http.authorizeRequests().antMatchers("/home").hasRole("USER");
		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");
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

}
