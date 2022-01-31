package com.tipikae.paymybuddy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.Connection;
import com.tipikae.paymybuddy.entities.ConnectionId;

/**
 * Connection repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface IConnectionRepository extends JpaRepository<Connection, ConnectionId> {

	/**
	 * Find a connection
	 * @param idSrc
	 * @param idDest
	 * @return Optional<ConnectionId>
	 */
	@Query(
		value = "SELECT * FROM connection WHERE id_user_src = :idSrc AND id_user_dest = :idDest",
		nativeQuery = true)
	Optional<Connection> findByConnectionId(
			@Param("idSrc") String idSrc, 
			@Param("idDest") String idDest);
}
