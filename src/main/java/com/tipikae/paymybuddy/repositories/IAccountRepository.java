package com.tipikae.paymybuddy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.Account;

/**
 * Account repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

	/**
	 * Find an account by user id.
	 * @param id
	 * @return Optional<Account>
	 */
	Optional<Account> findByIdUser(int id);
	
	/**
	 * Delete an account by user id.
	 * @param id
	 */
	void deleteByIdUser(int id);
}
