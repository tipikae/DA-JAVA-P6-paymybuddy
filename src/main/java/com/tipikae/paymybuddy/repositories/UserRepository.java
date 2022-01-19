package com.tipikae.paymybuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tipikae.paymybuddy.entities.Client;

/**
 * User repository interface.
 * @author tipikae
 * @version 1.0
 *
 */
@Repository
public interface UserRepository extends JpaRepository<Client, String> {

}
