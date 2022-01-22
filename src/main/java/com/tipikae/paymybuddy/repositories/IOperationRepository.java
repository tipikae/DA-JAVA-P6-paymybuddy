package com.tipikae.paymybuddy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.Account;
import com.tipikae.paymybuddy.entities.Operation;

/**
 * Operation repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface IOperationRepository extends JpaRepository<Operation, Integer> {

	List<Operation> findByAccount(Account account);
}
