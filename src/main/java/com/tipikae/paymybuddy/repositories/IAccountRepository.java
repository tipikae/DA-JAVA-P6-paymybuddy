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
	 * Find an account by number.
	 * @param number
	 * @return Optional<Account>
	 */
	Optional<Account> findByNumber(int number);
	
	/**
	 * Delete an account by number.
	 * @param number
	 */
	void deleteByNumber(int number);
}
