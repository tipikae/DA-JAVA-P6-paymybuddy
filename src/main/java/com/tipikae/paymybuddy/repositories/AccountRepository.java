package com.tipikae.paymybuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tipikae.paymybuddy.entities.Account;

/**
 * Account repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
