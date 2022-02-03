package com.tipikae.paymybuddy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.Operation;

/**
 * Operation repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface IOperationRepository extends JpaRepository<Operation, Integer> {

	/**
	 * Find operations by id account.
	 * @param idAccount
	 * @return List<Operation>
	 */
	@Query(
		value = "SELECT * FROM operation WHERE id_account = :idAccount ORDER BY date_operation DESC",
		nativeQuery = true)
	Page<Operation> findOperationsByIdSrc(@Param("idAccount") int idAccount, Pageable pageable);
}
