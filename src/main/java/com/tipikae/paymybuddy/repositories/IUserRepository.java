package com.tipikae.paymybuddy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.User;

/**
 * User repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface IUserRepository extends JpaRepository<User, String> {

	/**
	 * Find an user by email.
	 * @param email
	 * @return Optional<User>
	 */
	Optional<User> findByEmail(String email);
	
	/**
	 * Delete an user by email.
	 * @param email
	 */
	void deleteByEmail(String email);
}
