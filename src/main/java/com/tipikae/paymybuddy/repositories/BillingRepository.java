package com.tipikae.paymybuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.Billing;
import com.tipikae.paymybuddy.entities.Operation;

/**
 * Billing repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {

	Billing findByOperation(Operation operation);
}
