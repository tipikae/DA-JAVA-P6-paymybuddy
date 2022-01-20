package com.tipikae.paymybuddy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.Client;

/**
 * Client repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

	/**
	 * Find a client by email.
	 * @param email
	 * @return Optional<Client>
	 */
	Optional<Client> findByEmail(String email);
	
	/**
	 * Delete a Client by email.
	 * @param email
	 */
	void deleteByEmail(String email);
}
