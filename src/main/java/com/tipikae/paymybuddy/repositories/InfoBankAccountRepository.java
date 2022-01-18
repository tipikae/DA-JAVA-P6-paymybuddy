package com.tipikae.paymybuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tipikae.paymybuddy.entities.InfoBankAccount;

/**
 * InfoBankAccount Repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface InfoBankAccountRepository extends JpaRepository<InfoBankAccount, String> {

}
