package com.tipikae.paymybuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tipikae.paymybuddy.entities.Connection;

/**
 * Connection repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {

}
