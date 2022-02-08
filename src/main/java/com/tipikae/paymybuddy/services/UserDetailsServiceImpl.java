package com.tipikae.paymybuddy.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tipikae.paymybuddy.entities.Role;
import com.tipikae.paymybuddy.entities.User;
import com.tipikae.paymybuddy.repositories.IUserRepository;

/**
 * An implementation of UserDetailsService.
 * @author tipikae
 * @version 1.0
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
	 * UserRepository interface.
	 */
	@Autowired
	private IUserRepository userRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		LOGGER.debug("loadUserByUsername: email=" + email);
		Optional<User> optional = userRepository.findByEmail(email);
		if(!optional.isPresent()) {
			throw new UsernameNotFoundException("No user found with email: " + email);
		}
		User user = optional.get();
        
        return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
	}
    
    private static List<GrantedAuthority> getAuthorities (List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        }
        return authorities;
    }

}
